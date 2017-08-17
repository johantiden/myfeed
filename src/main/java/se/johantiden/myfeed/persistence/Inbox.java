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

    public Optional<Document> find(long documentId) {
        return inbox.stream().filter(d -> d.getId() == documentId).findAny();
    }

    public boolean hasDocument(long documentId) {
        return find(documentId).isPresent();
    }
}
