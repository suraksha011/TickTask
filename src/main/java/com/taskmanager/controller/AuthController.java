package com.taskmanager.controller;

import com.taskmanager.model.AppUser;
import com.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("newUser", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("newUser") AppUser newUser, Model model) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            model.addAttribute("error", "That username is already taken.");
            return "register";
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        if (newUser.getDisplayName() == null || newUser.getDisplayName().trim().isEmpty()) {
            newUser.setDisplayName(newUser.getUsername());
        }
        userRepository.save(newUser);
        return "redirect:/login?registered";
    }

    @PostMapping("/profile/theme")
    public String updateTheme(@RequestParam String clockTheme,
                               org.springframework.security.core.Authentication authentication) {
        userRepository.findByUsername(authentication.getName()).ifPresent(user -> {
            user.setClockTheme(clockTheme);
            userRepository.save(user);
        });
        return "redirect:/";
    }
}
