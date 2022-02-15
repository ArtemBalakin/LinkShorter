package shorterURL.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class htmlController {

    @GetMapping(path = "/mainPage")
    public String redirectShorter() {
        return "main";
    }

    @GetMapping(path = "")
    public String main() {
        return "main";
    }

    @GetMapping(path = "/login")
    public String logIn() {
        return "login";
    }

    @GetMapping(path = "/registry")
    public String registry() {
        return "regForm";
    }
}

