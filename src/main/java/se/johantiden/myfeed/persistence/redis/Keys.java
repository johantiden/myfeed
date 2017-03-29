package se.johantiden.myfeed.persistence.redis;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.persistence.UserDocument;

public class Keys {

    public static Key<User> user(String userName) {
        return Key.create("user:"+userName);
    }

    public static Key<Document> document(Document document) {
        return document(document.pageUrl);
    }

    public static Key<Document> document(String pageUrl) {
        return Key.create("document:"+hash(pageUrl));
    }

    private static String hash(String hashMe) {
        return "" + hashMe.hashCode(); // TODO: Better hashcode wanted (e.g. truncated SHA1)
    }

    public static Key<RedisSortedSet<UserDocument>> userDocuments(Key<User> user) {
        return sortedSet(user + ":userDocuments");
    }

    public static Key<RedisSortedSet<UserDocument>> userDocuments(User user) {
        return userDocuments(user.getKey());
    }

    public static Key<RedisSortedSet<Document>> documents() {
        return sortedSet("documents");
    }

    public static Key<UserDocument> userDocument(Key<User> user, Key<Document> document) {
        return Key.create("" + user + ":" + document);
    }

    public static Key<Feed> feed(Feed feed) {

        String uniqueness = feed.getName() + feed.getFeedReaderParameters();
        return new Key<>(uniqueness);

    }

    public static Key<RedisSet<Document>> inbox() {
        return set("inbox");
    }









    public static <T> Key<RedisSet<T>> set(String setName) {
        return Key.create("sets:"+setName);
    }
    public static <T> Key<RedisSortedSet<T>> sortedSet(String setName) {
        return Key.create("zets:"+setName);
    }
}
