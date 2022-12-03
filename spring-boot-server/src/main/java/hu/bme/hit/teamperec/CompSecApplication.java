package hu.bme.hit.teamperec;

import hu.bme.hit.teamperec.data.util.DatabaseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class CompSecApplication {

    private final DatabaseHandler databaseHandler;

    public static void main(String[] args) {
        SpringApplication.run(CompSecApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> databaseHandler.initDatabase();
    }

}
