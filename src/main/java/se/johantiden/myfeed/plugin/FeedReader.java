package se.johantiden.myfeed.plugin;


import se.johantiden.myfeed.persistence.Document;

import java.util.List;

public interface FeedReader {

    List<Document> readAllAvailable();

}
