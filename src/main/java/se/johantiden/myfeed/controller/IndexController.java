package se.johantiden.myfeed.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.plugin.Entry;
import se.johantiden.myfeed.plugin.Feed;
import se.johantiden.myfeed.user.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static se.johantiden.myfeed.util.JCollections.flatMap;

@RestController
@EnableAutoConfiguration
public class IndexController {

    @RequestMapping("/rest/index")
    List<Entry> index() {

        List<Entry> outputs = getRealOutput();


        for (Entry entry : outputs) {
            System.out.println(entry);
        }


        return outputs;
    }

    private List<Entry> getRealOutput() {

        User johan = User.johan();

        List<Feed> feeds = johan.getFeeds();
        List<Entry> entries = flatMap(feeds, Feed::readAllAvailable);


        Comparator<Entry> comparator = Comparator.comparing(Entry::getPublishedDate);
        Comparator<Entry> reversed = comparator.reversed();
        Collections.sort(entries, reversed);
        return entries;
    }


}
