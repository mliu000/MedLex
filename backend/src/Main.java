import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"model", "test"})
public class Main {

    public static void main(String[] args) {
        // Run the Spring Boot application
        SpringApplication.run(Main.class, args);
    }
}