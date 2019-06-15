package org.tim16.booker.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/test")
public class TestController {

    public static final String HELLO_TEXT = "Jupii!";

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public @ResponseBody String sayHello() {
        return HELLO_TEXT;
    }
}
