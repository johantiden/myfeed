package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

public class InboxRepository {

    private final LinkedBlockingQueue<Document> inbox = new LinkedBlockingQueue<>();


    public Optional<Document> pop() {
        Document poll = inbox.poll();
        return Optional.ofNullable(poll);
    }

    public void put(Document document) {
        inbox.offer(document);
    }

    public Optional<Document> find(Key<Document> documentKey) {
        return inbox.stream().filter(d -> d.getKey().equals(documentKey)).findAny();
    }

    public boolean hasDocument(Key<Document> key) {
        return inbox.stream()
                .anyMatch(d -> d.getKey().equals(key));
    }
}
