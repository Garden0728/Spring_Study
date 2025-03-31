package study.membership.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/") //localhost/8080으로 들어오면 home 메서드가 호출이 된다.

    public String home(){
        return "home";
    }
}
