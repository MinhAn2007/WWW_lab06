package vn.edu.iuh.fit.lab06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@SpringBootApplication
public class Lab06Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab06Application.class, args);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("your_raw_password");
        System.out.println("Hashed Password: " + hashedPassword);
    }
    }


