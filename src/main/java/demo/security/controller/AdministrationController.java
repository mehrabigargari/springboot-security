package demo.security.controller;

import demo.security.dto.UserView;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdministrationController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    public AdministrationController(UserDetailsManager userDetailsManager,
                                    PasswordEncoder passwordEncoder,
                                    JdbcTemplate jdbcTemplate) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/administration")
    public String administration(Model model) {
        List<String> usernames = jdbcTemplate.queryForList("select username from users", String.class);

        List<UserView> users = new ArrayList<>();
        for (String username : usernames) {
            try {
                UserDetails ud = userDetailsManager.loadUserByUsername(username);
                UserView uv = new UserView(
                        ud.getUsername(),
                        ud.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()),
                        ud.isAccountNonLocked()
                );
                users.add(uv);
            } catch (Exception ex) {
            }
        }

        model.addAttribute("users", users);
        return "administrationPage";
    }

    @PostMapping("/administration/saveUser")
    public String saveUser(@RequestParam String mode,
                           @RequestParam(required = false) String originalUsername,
                           @RequestParam String username,
                           @RequestParam(required = false) String password,
                           @RequestParam(required = false, name = "roles") String[] roles,
                           RedirectAttributes ra) {

        List<GrantedAuthority> authorities = convertRoles(roles);

        try {
            if ("add".equalsIgnoreCase(mode)) {
                if (userDetailsManager.userExists(username)) {
                    ra.addFlashAttribute("error", "User already exists: " + username);
                    return "redirect:/administration";
                }
                if (!StringUtils.hasText(password)) {
                    ra.addFlashAttribute("error", "Password is required when creating a user.");
                    return "redirect:/administration";
                }

                UserDetails user = User.withUsername(username)
                        .password(passwordEncoder.encode(password))
                        .authorities(authorities)
                        .accountLocked(false)
                        .build();

                userDetailsManager.createUser(user);
                ra.addFlashAttribute("message", "User created: " + username);
            } else {
                String existing = originalUsername != null ? originalUsername : username;
                if (!userDetailsManager.userExists(existing)) {
                    ra.addFlashAttribute("error", "User not found: " + existing);
                    return "redirect:/administration";
                }

                UserDetails current = userDetailsManager.loadUserByUsername(existing);

                String passwordToUse = current.getPassword();
                if (StringUtils.hasText(password)) {
                    passwordToUse = passwordEncoder.encode(password);
                }

                UserDetails updated = User.withUsername(username)
                        .password(passwordToUse)
                        .authorities(authorities)
                        .accountLocked(!current.isAccountNonLocked())
                        .build();

                if (!username.equals(current.getUsername())) {
                    userDetailsManager.deleteUser(current.getUsername());
                    userDetailsManager.createUser(updated);
                } else {
                    userDetailsManager.updateUser(updated);
                }

                ra.addFlashAttribute("message", "User updated: " + username);
            }
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "Error saving user: " + ex.getMessage());
        }

        return "redirect:/administration";
    }

    @PostMapping("/administration/changeRoles")
    public String changeRoles(@RequestParam String username,
                              @RequestParam(required = false, name = "roles") String[] roles,
                              RedirectAttributes ra) {
        try {
            if (!userDetailsManager.userExists(username)) {
                ra.addFlashAttribute("error", "User not found: " + username);
                return "redirect:/administration";
            }

            UserDetails current = userDetailsManager.loadUserByUsername(username);
            List<GrantedAuthority> authorities = convertRoles(roles);

            UserDetails updated = User.withUserDetails(current)
                    .authorities(authorities)
                    .build();

            userDetailsManager.updateUser(updated);
            ra.addFlashAttribute("message", "Roles updated for: " + username);
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "Error changing roles: " + ex.getMessage());
        }

        return "redirect:/administration";
    }

    @PostMapping("/administration/deleteUser")
    public String deleteUser(@RequestParam String username, RedirectAttributes ra) {
        try {
            if (!userDetailsManager.userExists(username)) {
                ra.addFlashAttribute("error", "User not found: " + username);
                return "redirect:/administration";
            }
            userDetailsManager.deleteUser(username);
            ra.addFlashAttribute("message", "Deleted user: " + username);
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "Error deleting user: " + ex.getMessage());
        }
        return "redirect:/administration";
    }

    private List<GrantedAuthority> convertRoles(String[] roles) {
        if (roles == null) return Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        return Arrays.stream(roles)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(StringUtils::hasText)
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
