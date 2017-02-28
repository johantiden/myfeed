package se.johantiden.myfeed.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.output.OutputBean;

@RestController
@EnableAutoConfiguration
public class IndexController {

    @RequestMapping("/rest/index")
    OutputBean ping() {
        return new OutputBean("hej", "http://www.google.com", "http://www.google.com/thumb.png");
    }

}
