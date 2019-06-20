package org.tim16.booker.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/test")
public class TestController {

    public static final String HELLO_TEXT = "Jupii!";

    @GetMapping(path = "/hello")
    public @ResponseBody String sayHello() {
        return HELLO_TEXT;
    }
}
