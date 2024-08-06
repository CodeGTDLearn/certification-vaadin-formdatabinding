package com.certification.formdatabinding;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "formdatabinding")
public class AppDriver implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(AppDriver.class, args);
    }

}