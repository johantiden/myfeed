package se.johantiden.myfeed.reader;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.plugin.FeedReader;

import java.util.List;

public class EmptyReader implements FeedReader {
    @Override
    public List<Document> readAllAvailable() {
        return Lists.newArrayList();
    }
}
