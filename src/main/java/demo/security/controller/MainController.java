package demo.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String dashboard(){
        return "dashboardPage";
    }

    @GetMapping("/management")
    public String management() {
        return "management";
    }

    @GetMapping("/administration")
    public String administration() {
        return "administration";
    }
}
