package demo.security.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/showLoginPage")
    public String showLoginPage(){
        return "loginPage";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDeniedPage";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {

        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to change password.");
            return "redirect:/showLoginPage";
        }

        String username = principal.getName();

        if (newPassword == null || newPassword.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "Password must be at least 6 characters.");
            return "redirect:/";
        }

        UserDetails current = userDetailsManager.loadUserByUsername(username);

        UserDetails updated = User.withUserDetails(current)
                .password(passwordEncoder.encode(newPassword))
                .build();

        userDetailsManager.updateUser(updated);

        redirectAttributes.addFlashAttribute("message", "Password changed successfully.");
        return "redirect:/";
    }
}
