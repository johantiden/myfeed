package se.johantiden.myfeed.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.plugin.Entry;
import se.johantiden.myfeed.plugin.Feed;
import se.johantiden.myfeed.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static se.johantiden.myfeed.util.JCollections.filter;
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
        List<Entry> entries = flatMap(feeds, tryGetEntries());
        List<Entry> filteredEntries = filter(entries, johan.getFilters());

        Comparator<Entry> comparator = Comparator.comparing(Entry::getPublishedDate);
        Comparator<Entry> reversed = comparator.reversed();
        Collections.sort(filteredEntries, reversed);
        return filteredEntries;
    }

    private static Function<Feed, Collection<Entry>> tryGetEntries() {
        return f -> {
            try {
                return f.readAllAvailable();
            } catch (RuntimeException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        };
    }

}
