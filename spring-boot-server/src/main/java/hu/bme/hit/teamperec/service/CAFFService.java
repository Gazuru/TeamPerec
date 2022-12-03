package hu.bme.hit.teamperec.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import hu.bme.hit.teamperec.config.security.services.UserDetailsImpl;
import hu.bme.hit.teamperec.data.ComputerSecurityException;
import hu.bme.hit.teamperec.data.dto.CAFFDto;
import hu.bme.hit.teamperec.data.entity.CAFF;
import hu.bme.hit.teamperec.data.repository.CAFFRepository;
import hu.bme.hit.teamperec.data.response.CAFFDownloadResponse;
import hu.bme.hit.teamperec.data.response.CAFFResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class CAFFService {

    private final CAFFRepository caffRepository;

    private final UserService userService;

    public List<CAFFResponse> getCaffs(String uploaderName, String name) {
        Set<CAFF> uploaderResults = null;
        Set<CAFF> nameResults = null;

        if ((!StringUtils.hasText(uploaderName) && !StringUtils.hasText(name)) || (uploaderName.equals("null") && name.equals("null"))) {
            return caffRepository.findAll().stream().map(CAFF::toResponse).toList();
        }

        if (StringUtils.hasText(uploaderName)) {
            uploaderResults =
                    new HashSet<>(caffRepository.findAllByUploaderUsernameContainsIgnoreCaseOrderByUploadedAtDesc(uploaderName));
        }
        if (StringUtils.hasText(name)) {
            nameResults = new HashSet<>(caffRepository.findAllByNameContainsIgnoreCaseOrderByUploadedAtDesc(name));
        }

        return getOneOrIntersection(uploaderResults, nameResults).stream().map(CAFF::toResponse).toList();
    }

    private Set<CAFF> getOneOrIntersection(Set<CAFF> first, Set<CAFF> second) {
        if (CollectionUtils.isEmpty(first) && !CollectionUtils.isEmpty(second)) {
            return second;
        } else if (!CollectionUtils.isEmpty(first) && CollectionUtils.isEmpty(second)) {
            return first;
        } else if (!CollectionUtils.isEmpty(first) && !CollectionUtils.isEmpty(second)) {
            first.retainAll(second);
            return first;
        } else {
            return Collections.emptySet();
        }
    }

    public List<CAFFResponse> getCaffsOfUser(UUID uploaderId) {
        return caffRepository.findAllByUploaderIdOrderByUploadedAtDesc(uploaderId).stream().map(CAFF::toResponse).toList();
    }

    public CAFFResponse uploadCaff(CAFFDto caffDto) {
        var caff = toCaffFromDto(new CAFF(), caffDto);
        return caffRepository.save(caff).toResponse();
    }

    private CAFF toCaffFromDto(CAFF caff, CAFFDto caffDto) {
        var name = caffDto.name();
        var description = caffDto.description();
        var image = caffDto.imageBase64();

        if (Objects.isNull(caff.getUploader())) {
            var username = SecurityContextHolder.getContext().getAuthentication().getName();
            var uploader = userService.getUserByUsername(username);
            caff.setUploader(uploader);
        }
        if (Objects.nonNull(name)) {
            caff.setName(name);
        }
        if (Objects.nonNull(description)) {
            caff.setDescription(caffDto.description());
        }
        if (Objects.nonNull(image)) {
            caff.setCaffEncodedString(image);
            caff.setGifEncodedString(parseCaffContents(image));
        }

        return caff;
    }

    public void deleteCaff(UUID caffId) {
        if (caffRepository.existsById(caffId)) {
            caffRepository.deleteById(caffId);
        }
    }

    public CAFFResponse getCaff(UUID caffId) {
        return getCaffById(caffId).toResponse();
    }

    public CAFF getCaffById(UUID caffId) {
        return caffRepository.findById(caffId).orElseThrow(
                () -> new ComputerSecurityException("CAFF not found by id: " + caffId));
    }

    public CAFFDownloadResponse downloadCaff(UUID caffId) {
        var caff = getCaffById(caffId);
        var user =
                userService.getUserById(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        userService.addDownload(user, caff);

        return new CAFFDownloadResponse(caff.getCaffEncodedString());
    }

    public CAFFResponse updateCaff(UUID caffId, CAFFDto caffDto) {
        var caff = toCaffFromDto(getCaffById(caffId), caffDto);
        return caffRepository.save(caff).toResponse();
    }

    private String parseCaffContents(String base64encodedString) {
        try {
            var os = System.getProperty("os.name").toLowerCase();
            var path = "";

            if (os.contains("win")) {
                path = "./native/caff_parser.exe";
            } else {
                path = "./native/caff_parser";
            }

            var caffByteArray = Base64.getDecoder().decode(base64encodedString);
            Files.write(Paths.get("temp"), caffByteArray);

            String[] exec = {path, "./temp", "generated_caff"};

            return executeCommand(exec);
        } catch (IOException | InterruptedException e) {
            throw new ComputerSecurityException(e.getMessage());
        }
    }

    private String executeCommand(String[] command) throws IOException, InterruptedException {

        Process process = new ProcessBuilder().inheritIO().command(command).start();

        Scanner error = new Scanner(process.getErrorStream());

        StringBuilder errorMessage = new StringBuilder();
        while (error.hasNextLine()) {
            errorMessage.append(error.nextLine());
        }

        process.waitFor();

        if (process.exitValue() == 0) {
            return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get("generated_caff.gif")));
        } else {
            throw new ComputerSecurityException(errorMessage.toString());
        }
    }

}
