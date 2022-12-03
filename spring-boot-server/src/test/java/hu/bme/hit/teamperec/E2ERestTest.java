package hu.bme.hit.teamperec;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.hit.teamperec.data.dto.CAFFDto;
import hu.bme.hit.teamperec.data.dto.LoginRequest;
import hu.bme.hit.teamperec.data.repository.RoleRepository;
import hu.bme.hit.teamperec.data.response.CAFFDownloadResponse;
import hu.bme.hit.teamperec.data.response.CAFFResponse;
import hu.bme.hit.teamperec.data.response.JwtResponse;
import hu.bme.hit.teamperec.service.CAFFService;
import hu.bme.hit.teamperec.service.CommentService;
import hu.bme.hit.teamperec.service.UserService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class E2ERestTest {

    @Autowired
    private CAFFService caffService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    private Header authHeader;

    private byte[] caffFile1;

    private byte[] caffFile2;

    private byte[] caffFile3;

    @BeforeAll
    public void setup() throws JSONException {
        var adminToken = login("admin", "admin");
        authHeader = new Header("Authorization", "Bearer " + adminToken);
        caffFile1 = readFileToBytes("classpath:1.caff");
        caffFile2 = readFileToBytes("classpath:2.caff");
        caffFile3 = readFileToBytes("classpath:3.caff");
    }

    @Test
    void shouldUploadCaff() {
        var test = "shouldUploadCaff";
        var caffDto = new CAFFDto(test + "_name", test + "_description", Base64.getEncoder().encodeToString(caffFile1));

        var caffResponse = RestAssured.given()
                .header(authHeader)
                .contentType(ContentType.JSON)
                .body(caffDto)
                .post("http://localhost:" + port + "/api/caff/upload")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CAFFResponse.class);

        assertThat(caffResponse.name()).isEqualTo(caffDto.name());
        assertThat(caffResponse.description()).isEqualTo(caffDto.description());
        assertThat(userService.getUserById(caffResponse.uploader()).getUsername()).isEqualTo("admin");
    }

    @Test
    void shouldGetCaffs() {
        var test = "shouldGetCaffs";
        var caff1 = createCaff(test + "_caff1", test + "_desc1", caffFile1);
        var caff2 = createCaff(test + "_caff2", test + "_desc2", caffFile2);

        var ids = List.of(caff1.id(), caff2.id());

        var getResponse = RestAssured.given()
                .header(authHeader)
                .contentType(ContentType.JSON)
                .get("http://localhost:" + port + "/api/caff/list")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(".", CAFFResponse.class).stream().map(CAFFResponse::id).toList();

        assertThat(getResponse).hasSizeGreaterThan(0).hasSameSizeAs(ids).containsExactlyElementsOf(ids);
    }

    @Test
    void shouldGetCaffsByUsername() {
        var test = "shouldGetCaffs";
        var caff1 = createCaff(test + "_caff1", test + "_desc1", caffFile1);
        var caff2 = createCaff(test + "_caff2", test + "_desc2", caffFile2);

        var ids = List.of(caff1.id(), caff2.id());

        var getResponses = RestAssured.given()
                .header(authHeader)
                .contentType(ContentType.JSON)
                .get("http://localhost:" + port + "/api/caff/list?uploaderName=admin")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(".", CAFFResponse.class).stream().map(CAFFResponse::id).toList();

        assertThat(getResponses).hasSizeGreaterThan(0).hasSameSizeAs(ids).containsExactlyElementsOf(ids);
    }

    @Test
    void shouldGetCaffsByName() {
        var test = "shouldGetCaffsByName";
        var caff1 = createCaff(test + "_caff1", test + "_desc1", caffFile1);
        createCaff(test + "_caff2", test + "_desc2", caffFile2);

        var getResponses = RestAssured.given()
                .header(authHeader)
                .contentType(ContentType.JSON)
                .get("http://localhost:" + port + "/api/caff/list?name=" + caff1.name())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(".", CAFFResponse.class).stream().map(CAFFResponse::id).toList();

        assertThat(getResponses).hasSizeGreaterThan(0).hasSize(1).containsExactly(caff1.id());
    }

    @Test
    void shouldGetCaffsOfUser() {
        var test = "shouldGetCaffsOfUser";
        var caff1 = createCaff(test + "_caff1", test + "_desc1", caffFile1);
        var caff2 = createCaff(test + "_caff2", test + "_desc2", caffFile2);

        var ids = List.of(caff1.id(), caff2.id());

        var getResponses = RestAssured.given()
                .header(authHeader)
                .contentType(ContentType.JSON)
                .get("http://localhost:" + port + "/api/caff/" + caff1.uploader() + "/caffs")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(".", CAFFResponse.class).stream().map(CAFFResponse::id).toList();

        assertThat(getResponses).hasSizeGreaterThan(0).hasSize(2).containsExactlyElementsOf(ids);
    }

    @Test
    void shouldGetCaff() {
        var test = "shouldGetCaff";
        var caff1 = createCaff(test + "_caff1", test + "_desc1", caffFile1);
        createCaff(test + "_caff2", test + "_desc2", caffFile2);

        var responseId = RestAssured.given()
                .header(authHeader)
                .contentType(ContentType.JSON)
                .get("http://localhost:" + port + "/api/caff/" + caff1.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .as(CAFFResponse.class).id();

        assertThat(responseId).isEqualTo(caff1.id());
    }

    @Test
    void shouldDownloadCaff() {
        var test = "shouldDownloadCaff";
        var caff1 = createCaff(test + "_caff1", test + "_desc1", caffFile1);

        var responseBase64 = RestAssured.given()
                .header(authHeader)
                .contentType(ContentType.JSON)
                .get("http://localhost:" + port + "/api/caff/" + caff1.id() + "/download")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .as(CAFFDownloadResponse.class).caffBase64();

        var caff = caffService.getCaffById(caff1.id());

        assertThat(responseBase64).isEqualTo(caff.getCaffEncodedString());
    }

    @Test
    void shouldDeleteCaff() {
        var test = "shouldDeleteCaff";
        var caff1 = createCaff(test + "_caff1", test + "_desc1", caffFile1);
        createCaff(test + "_caff2", test + "_desc2", caffFile2);

        var getResponses = RestAssured.given()
                .header(authHeader)
                .contentType(ContentType.JSON)
                .get("http://localhost:" + port + "/api/caff/list")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .jsonPath()
                .getList(".", CAFFResponse.class);

        assertThat(getResponses).hasSize(2);

        RestAssured.given()
                .header(authHeader)
                .contentType(ContentType.JSON)
                .delete("http://localhost:" + port + "/api/caff/" + caff1.id())

        getResponses

        assertThat(responseId).isEqualTo(caff1.id());
    }


    private String login(String username, String password) throws JSONException {
        var loginRequest = new LoginRequest(username, password);

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("http://localhost:" + port + "/api/auth/signin")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(JwtResponse.class);

        return response.getToken();
    }

    private byte[] readFileToBytes(String path) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        var resource = resourceLoader.getResource(path);
        try {
            return Files.readAllBytes(Paths.get(resource.getFile().getPath()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private CAFFResponse createCaff(String name, String description, byte[] caffFile) {
        return RestAssured.given()
                .header(authHeader)
                .contentType(ContentType.JSON)
                .body(new CAFFDto(name, description, Base64.getEncoder().encodeToString(caffFile)))
                .post("http://localhost:" + port + "/api/caff/upload")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CAFFResponse.class);
    }

}
