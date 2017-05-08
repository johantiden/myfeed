package se.johantiden.myfeed.persistence.redis;

import se.johantiden.myfeed.controller.Subject;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.persistence.UserDocument;

public class Keys {

    public static Key<User> user(String userName) {
        return Key.create(userName);
    }

    public static Key<Document> document(String pageUrl) {
        return Key.create("d:"+hash(pageUrl));
    }

    private static String hash(String hashMe) {
        return Sha1Hex.makeSHA1Hash(hashMe);
    }

    public static Key<UserDocument> userDocument(Key<User> user, Key<Document> document) {
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
