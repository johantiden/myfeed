package se.johantiden.myfeed.persistence.redis;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.User;
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

    public static <T> Key<RedisSet<T>> set(String setName) {
        return Key.create("sets:"+setName);
    }

    public static Key<RedisSet<UserDocument>> userDocuments(Key<User> user) {
        return set(user + ":userDocuments");
    }

    public static Key<RedisSet<UserDocument>> userDocuments(User user) {
        return userDocuments(user.getKey());
    }

    public static Key<RedisSet<Document>> documents() {
        return set("documents");
    }

    public static Key<UserDocument> userDocument(Key<User> user, Key<Document> document) {
        return Key.create("" + user + document);
    }
}
