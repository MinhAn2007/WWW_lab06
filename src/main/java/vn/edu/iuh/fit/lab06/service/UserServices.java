package vn.edu.iuh.fit.lab06.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.lab06.models.User;
import vn.edu.iuh.fit.lab06.repositories.UserRepository;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServices {
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public User login(String email, String password) {
        return  userRepository.findByEmailAndPasswordHash(email, password).orElse(null);
    }
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email đã được sử dụng.");
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setRegisteredAt(Instant.now());
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUserProfile(User user, String newIntro, String newProfile) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setIntro(newIntro);
            updatedUser.setProfile(newProfile);
            userRepository.save(updatedUser);
        } else {
            throw new RuntimeException("Người dùng không tồn tại.");
        }
    }
}
