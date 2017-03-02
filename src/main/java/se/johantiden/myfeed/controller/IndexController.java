package se.johantiden.myfeed.controller;

import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.output.OutputBean;
import se.johantiden.myfeed.plugin.reddit.TheLocalReader;
import se.johantiden.myfeed.plugin.rss.RssReader;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class IndexController {

    @RequestMapping("/rest/index")
    List<OutputBean> index() {

        List<OutputBean> outputs = getRealOutput();


        for (OutputBean entry : outputs) {
            System.out.println(entry);
        }


        return outputs;
    }

    private ArrayList<OutputBean> getFakeOutput() {
        return Lists.newArrayList(
                new OutputBean("hej1", "http://www.google.com", "http://www.google.com/thumb.png", Instant.now()),
                new OutputBean("hej2", "http://www.google.com", "http://www.google.com/thumb.png", Instant.now().minusSeconds(2))
        );
    }

    private List<OutputBean> getRealOutput() {

        List<OutputBean> all = new ArrayList<>();

        all.addAll(new TheLocalReader().readAll());
        all.addAll(new RssReader("http://computersweden.idg.se/rss/systemutveckling").readAll());
//        all.addAll(new RssReader("http://computersweden.idg.se/rss/forskning").readAll());
        all.addAll(new RssReader("http://computersweden.idg.se/rss/prylar").readAll());

        Collections.sort(all, Comparator.comparing(OutputBean::getPublishedDate).reversed());
        return all;
    }


}
