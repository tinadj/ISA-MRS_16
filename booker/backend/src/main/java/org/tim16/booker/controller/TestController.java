package org.tim16.booker.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/test")
public class TestController {

    public static final String HELLO_TEXT = "Jupii!";

    @RequestMapping(path = "/hello")
    public @ResponseBody String sayHello() {
        return HELLO_TEXT;
    }
}
