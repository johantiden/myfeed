package se.johantiden.myfeed.controller;

import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.output.OutputBean;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class IndexController {

    @RequestMapping("/rest/index")
    List<OutputBean> index() {
        return Lists.newArrayList(
                new OutputBean("hej1", "http://www.google.com", "http://www.google.com/thumb.png"),
                new OutputBean("hej2", "http://www.google.com", "http://www.google.com/thumb.png")
        );
    }

}
