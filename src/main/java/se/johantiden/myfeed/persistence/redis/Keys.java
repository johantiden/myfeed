package se.johantiden.myfeed.persistence.redis;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentGroup;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.persistence.UserDocument;

import java.security.acl.Group;

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

    public static Key<RedisIndexedMap<Key<UserDocument>, UserDocument>> userDocuments(Key<User> user) {
        return indexedMap("ud:" + user);
    }

    public static Key<RedisIndexedMap<Key<Document>, Document>> documents() {
        return indexedMap("d");
    }


    public static Key<UserDocument> userDocument(Key<User> user, Key<Document> document) {
        return Key.create(user + ":" + document);
    }

    public static Key<Feed> feed(Feed feed) {

        String uniqueness = feed.getName() + feed.getFeedReaderParameters();
        return new Key<>(uniqueness);

    }

    public static Key<RedisMap<Key<Document>, Document>> inbox() {
        return Key.create("sets:inbox");
    }

    private static <T> Key<RedisIndexedMap<Key<T>, T>> indexedMap(String mapName) {
        return Key.create("maps:"+mapName);
    }

    public static Key<RedisMap<Key<Document>, Key<Document>>> verySimilarGrouper() {
        return Key.create("sets:grouper:very-similar");
    }

    public static Key<RedisMap<Key<Document>, Key<DocumentGroup>>> findGroupByDocument() {
        return Key.create("sets:find-group-by-document");
    }
}
