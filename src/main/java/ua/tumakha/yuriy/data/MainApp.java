package ua.tumakha.yuriy.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Yuriy Tumakha
 */
@SpringBootApplication
public class MainApp {

    private static final String APP_USAGE = "\nEmails Filter Usage:\n" +
            "java -jar emails-filter.jar emails.txt domains.txt\n" +
            "java -jar emails-filter.jar emails.txt domains.txt filtered.emails.txt\n\n";

    public static void main(String[] args) {
        info(APP_USAGE);
        SpringApplication.run(MainApp.class, args);
    }

    private static void info(String message) {
        System.out.println(message);
    }

}
