package se.johantiden.myfeed.persistence.redis;

import se.johantiden.myfeed.controller.Subject;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.Username;

public class Keys {

    public static Username user(String userName) {
        return new Username(userName);
    }

    public static Key<Document> document(String pageUrl) {
        return Key.create("d:"+hash(pageUrl));
    }

    private static String hash(String hashMe) {
        return Sha1Hex.makeSHA1Hash(hashMe);
    }

    public static Key<UserDocument> userDocument(Username user, Key<Document> document) {
        return Key.create(user + ":" + document);
    }

    public static Key<Feed> feed(Feed feed) {

        String uniqueness = feed.getName() + feed.getFeedReaderParameters();
        return new Key<>(uniqueness);

    }

    public static Key<Subject> subject(String keySeed) {
        return Key.create("s:"+hash(keySeed));
    }
}
