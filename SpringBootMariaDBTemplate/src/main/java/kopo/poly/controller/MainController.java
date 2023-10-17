package kopo.poly.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/abc")
    public String abc() throws Exception {
        log.info(this.getClass().getName() + ".abc Start!");
        return "/abc";
    }

    @GetMapping("/index")
    public String index() throws Exception {
        log.info(this.getClass().getName() + ".index Start!");
        return "/index";
    }

    @GetMapping("/main")
    public String main() throws Exception {
        log.info(this.getClass().getName() + ".index Start!");
        return "main";
    }
}
