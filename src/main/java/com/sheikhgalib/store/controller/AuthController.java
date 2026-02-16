package com.sheikhgalib.store.controller;

import com.sheikhgalib.store.entity.User;
import com.sheikhgalib.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Set;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                       @RequestParam(required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String role,
                          Model model) {
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        if (userRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of("ROLE_" + role.toUpperCase()));
        user.setEnabled(true);

        userRepository.save(user);

        return "redirect:/login?registered=true";
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "dashboard";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
