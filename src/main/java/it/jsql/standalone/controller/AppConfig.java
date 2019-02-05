package it.jsql.standalone.controller;

import it.jsql.connector.exceptions.JSQLException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import it.jsql.DatabaseApplication;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class AppConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username(DatabaseApplication.USERNAME)
                .password(DatabaseApplication.PASSWORD)
                .url(getURL())
                .driverClassName(getDriver())
                .build();
    }

    private static String getURL() {
        String url = "";
        try {
            String databaseType = getDatabaseDialect();
            if (databaseType.equals("POSTGRES")) {
                url = "jdbc:postgresql://" + DatabaseApplication.URL;
            } else if (databaseType.equals("MYSQL")) {
                url = "jdbc:mysql://" + DatabaseApplication.URL;
            } else {
                throw new JSQLException("Database type not configured");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }

    private static String getDriver() {
        String driverClassName = "";
        try {
            String databaseType = getDatabaseDialect();
            if (databaseType.equals("POSTGRES")) {
                driverClassName = "org.postgresql.Driver";
            } else if (databaseType.equals("MYSQL")) {
                driverClassName = "com.mysql.jdbc.Driver";
            } else {
                throw new JSQLException("Database type not configured");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return driverClassName;
    }

    public static String getDatabaseDialect() throws Exception {

        StringBuilder result = new StringBuilder();
        URL url = new URL("http://softwarecartoon.com:9391/api/request/options/all");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("ApiKey", DatabaseApplication.API_KEY);
        conn.setRequestProperty("MemberKey", DatabaseApplication.MEMBER_KEY);
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        String[] words = result.toString().split(",");
        for (int i = 0; i < words.length; i++) {
            if (words[i].contains("databaseDialect")) {
                String[] dialect = words[i].split(":");
                return dialect[1].substring(1, dialect[1].length() - 1);
            }
        }

        throw new Error("Bad API_KEY or MEMBER_KEY!");
    }
}
