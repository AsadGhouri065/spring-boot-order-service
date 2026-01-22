package com.ghouri.orderservice.service;

import com.ghouri.orderservice.model.User;
import com.ghouri.orderservice.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserServiceSRP {
    private final UserRepository userRepository;
    private final Validator validator;
    private final Notification notification;
    private final CustomLogger customLogger;

    public UserServiceSRP(UserRepository userRepository, Validator validator, Notification notification, CustomLogger customLogger) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.notification = notification;
        this.customLogger = customLogger;
    }

    public void registerUser(String username, String password, String email) {
        validator.validatePassword(password);
        validator.validateEmail(email);

        User user = new User(username, password, email);
        userRepository.save(user);

        notification.send();
        customLogger.log();
    }
}

@Component
class Validator {
    public void validatePassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password too short");
        }
    }

    public void validateEmail(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

}

@Component
class Notification {
    public void send() {
        System.out.println("Sending notification...");
    }
}

@Component
class CustomLogger {
    public void log() {
        System.out.println("Logging...");
    }
}

