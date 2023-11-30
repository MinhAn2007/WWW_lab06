package vn.edu.iuh.fit.lab06.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.iuh.fit.lab06.models.User;
import vn.edu.iuh.fit.lab06.repositories.UserRepository;
import vn.edu.iuh.fit.lab06.service.UserServices;

import java.time.Instant;

@Controller
@RequestMapping("/users")
@NoArgsConstructor
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") String id) {
        return "";
    }

    @GetMapping("/add-form")
    public String show(@ModelAttribute User user, Model model) {
        user = new User();
        model.addAttribute("user", user);
        return "users/add-form";
    }

    public String add(@ModelAttribute User user, Model model) {
        userRepository.save(user);
        return "/";
    }

    @GetMapping("/login-form")
    public String showLogin(@ModelAttribute User user, Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, RedirectAttributes redirectAttributes, @RequestParam("password") String password) {
        User loggedInUser = userServices.login(user.getEmail(), password);
        if (loggedInUser != null) {
            loggedInUser.setLastLogin(Instant.now());
            userServices.save(loggedInUser);
            httpSession.setAttribute("loggedInUser", loggedInUser);
            return "redirect:/posts/posts";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/users/login-form";
        }


    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login-form";
    }
    @GetMapping("/register-form")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model, @RequestParam("password") String password) {
        user.setRegisteredAt(Instant.now());
        System.out.println(password);
        user.setPasswordHash(passwordEncoder.encode(password));
        userRepository.save(user);
        model.addAttribute("message", "Registration successful. Please login.");
        return "user/login";
    }
    @GetMapping("/details/{userId}")
    public String showUserDetails(@PathVariable Long userId, Model model) {
        User user = userRepository.findById(userId)
                .orElse(null);
        model.addAttribute("user", user);
        return "user/userDetails";
    }

    @GetMapping("/update/{userId}")
    public String showUpdateForm(@ModelAttribute User user, Model model,@PathVariable long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        model.addAttribute("user", existingUser);
        return "user/updateUser";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User updatedUser) {
        userServices.save(updatedUser);
        return "redirect:/users/details/" + updatedUser.getId();
    }
}
