package it.jsql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatabaseApplication {
    public static String API_KEY, MEMBER_KEY, URL, USERNAME, PASSWORD;

    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("You have to pass 5 arguments, example usage:");
            System.out.println("java -jar app.jar apiKey memberKey databaseUrl databaseUsername databasePassword");
        } else {
            API_KEY = args[0];
            MEMBER_KEY = args[1];
            URL = args[2];
            USERNAME = args[3];
            PASSWORD = args[4];
            SpringApplication.run(DatabaseApplication.class, args);
        }
    }


}
