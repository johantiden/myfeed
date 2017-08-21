package se.johantiden.myfeed.persistence;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

public class Inbox {

    private final LinkedBlockingQueue<Document> inbox = new LinkedBlockingQueue<>();


    public Optional<Document> pop() {
        Document poll = inbox.poll();
        return Optional.ofNullable(poll);
    }

    public void put(Document document) {
        inbox.offer(document);
    }

    public Optional<Document> find(String pageUrl) {
        return inbox.stream().filter(d -> d.getPageUrl().equals(pageUrl)).findAny();
    }

    public boolean hasDocument(Document document) {
        return find(document.getPageUrl()).isPresent();
    }
}
