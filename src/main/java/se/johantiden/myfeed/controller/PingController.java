package se.johantiden.myfeed.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class PingController {

    @RequestMapping("/ping")
    String ping() {
        return "pong!";
    }

}
