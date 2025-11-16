package demo.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class MainController {

    @GetMapping("/")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() != null
                && !"anonymousUser".equals(auth.getPrincipal())) {

            String roles = auth.getAuthorities().stream()
                    .map(ga -> ga.getAuthority().replaceFirst("^ROLE_", ""))
                    .collect(Collectors.joining(", "));

            model.addAttribute("rolesSimple", roles);
        } else {
            model.addAttribute("rolesSimple", "");
        }
        return "dashboardPage";
    }

    @GetMapping("/management")
    public String management() {
        return "management";
    }

}
