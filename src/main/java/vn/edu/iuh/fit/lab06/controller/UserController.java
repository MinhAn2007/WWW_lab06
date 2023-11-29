package vn.edu.iuh.fit.lab06.controller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.lab06.models.User;
import vn.edu.iuh.fit.lab06.repositories.UserRepository;
import vn.edu.iuh.fit.lab06.service.UserServices;

import java.time.Instant;

@Controller
@RequestMapping("/users")
@NoArgsConstructor @AllArgsConstructor
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") String id){
        return "";
    }

    @GetMapping("/add-form")
    public String show(@ModelAttribute User user, Model model){
        user =new User();
        model.addAttribute("user",user);
        return "users/add-form";
    }
    public String add(@ModelAttribute User user, Model model){
        userRepository.save(user);
        return "/";
    }
    @GetMapping("/show-login-page")
    public String show_login(@ModelAttribute User user, Model model){
        model.addAttribute("user", new User());
        return "users/login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model){
        User us = userServices.login(user.getEmail(), user.getPasswordHash());
        return "index";
    }
    @GetMapping("/register-form")
    public String showRegistrationForm(Model model ) {
        User user = new User();
        model.addAttribute("user",user);
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model,@RequestParam("password") String password) {
        user.setRegisteredAt(Instant.now());
        System.out.println(password);
        user.setPasswordHash(passwordEncoder.encode(password));
        userRepository.save(user);
        model.addAttribute("message", "Registration successful. Please login.");
        return "user/login";
    }
}
