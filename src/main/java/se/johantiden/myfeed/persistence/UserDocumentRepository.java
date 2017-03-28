package se.johantiden.myfeed.persistence;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.redis.RedisSet;
import se.johantiden.myfeed.persistence.user.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDocumentRepository {


    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private Gson gson;

    public List<UserDocument> getUnreadDocuments(Key<User> user) {
        return getUserDocuments(user)
                .stream()
                .filter(UserDocument::isUnread)
                .collect(Collectors.toList());

    }

    private Set<UserDocument> getUserDocuments(Key<User> user) {
        return getRedisSet(user).getAll(UserDocument.class);
    }

    public void put(UserDocument userDocument) {
        getRedisSet(userDocument.getUserKey()).put(userDocument, UserDocument::getKey, UserDocument.class);
    }

    public Optional<UserDocument> find(Key<User> user, Key<Document> documentKey) {
       return getRedisSet(user).find(ud -> ud.getDocumentKey().equals(documentKey), UserDocument.class);
    }

    private RedisSet<UserDocument> getRedisSet(Key<User> user) {
        return new RedisSet<>(Keys.userDocuments(user), jedisPool, gson);
    }

}
