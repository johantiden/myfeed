package se.johantiden.myfeed.persistence.redis;

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

    public static Key<RedisMap<UserDocument>> userDocuments(Key<User> user) {
        return indexedMap("ud:" + user);
    }

    public static Key<RedisMap<Document>> documents() {
        return indexedMap("d");
    }


    public static Key<UserDocument> userDocument(Key<User> user, Key<Document> document) {
        return Key.create(user + ":" + document);
    }

    public static Key<Feed> feed(Feed feed) {

        String uniqueness = feed.getName() + feed.getFeedReaderParameters();
        return new Key<>(uniqueness);

    }

    public static Key<RedisSet<Document>> inbox() {
        return set("inbox");
    }








    private static <T> Key<RedisMap<T>> indexedMap(String mapName) {
        return Key.create("maps:"+mapName);
    }

    public static <T> Key<RedisSet<T>> set(String setName) {
        return Key.create("sets:"+setName);
    }
}
