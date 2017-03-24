package se.johantiden.myfeed.persistence.redis;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import se.johantiden.myfeed.persistence.Persistable;

import java.lang.reflect.Type;
import java.util.function.Function;


public class JedisClient {

    @Autowired
    private Gson gson;

    @Autowired
    private JedisPool pool;

    public void save(Persistable<?> object) {
        saveString(object.getKey(), gson.toJson(object));
    }
    public void save(Key<?> key, Object object) {
        saveString(key, gson.toJson(object));
    }

    public void saveString(Key key, String value) {
        withJedis(jedis -> jedis.set(key.toString(), value));
    }

    public <T> T load(Key<T> key, Type type) {
        String json = loadString(key.toString());
        return gson.fromJson(json, type);
    }

    public String loadString(String key) {
        return withJedis(jedis -> jedis.get(key));
    }

    public <T> T withJedis(Function<Jedis, T> function) {
        try (Jedis jedis = pool.getResource()){
            return function.apply(jedis);
        }
    }
}
