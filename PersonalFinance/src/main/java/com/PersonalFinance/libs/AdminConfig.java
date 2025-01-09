package com.PersonalFinance.libs;

import java.io.IOException;
import java.util.Properties;

public class AdminConfig {
    public static final String ADMIN_EMAIL;
    public static final String ADMIN_PASSWORD;
    public static final String ADMIN_USERNAME;

    static {
        // Try to load from environment variables first
        String email = System.getenv("ADMIN_EMAIL");
        String password = System.getenv("ADMIN_PASSWORD");
        String username = System.getenv("ADMIN_USERNAME");

        // If environment variables are not set, fallback to config.properties
        if (email == null || password == null || username == null) {
            Properties props = new Properties();
            try {
                props.load(AdminConfig.class.getClassLoader().getResourceAsStream("config.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load admin credentials from config.properties", e);
            }

            email = props.getProperty("ADMIN_EMAIL", "");
            password = props.getProperty("ADMIN_PASSWORD", "");
            username = props.getProperty("ADMIN_USERNAME", "");
        }

        ADMIN_EMAIL = email;
        ADMIN_PASSWORD = password;
        ADMIN_USERNAME = username;


    }
}
