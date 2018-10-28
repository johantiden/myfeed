package se.johantiden.myfeed.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@CrossOrigin
@RestController
@EnableAutoConfiguration
public class ErrorController {

    @RequestMapping(value = "/error", method = GET)
    public String error() {
        return "There was an error :(";
    }

}
