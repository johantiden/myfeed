package se.johantiden.myfeed;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import se.johantiden.myfeed.persistence.DocumentRepository;
import se.johantiden.myfeed.persistence.FeedRepository;
import se.johantiden.myfeed.persistence.InboxRepository;
import se.johantiden.myfeed.persistence.UserDocumentRepository;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.persistence.user.UserRepository;
import se.johantiden.myfeed.reader.FeedReaderService;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.FeedService;
import se.johantiden.myfeed.service.InboxService;
import se.johantiden.myfeed.service.UserDocumentService;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@EnableScheduling
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    @Bean
    public DocumentRepository documentRepository() {
        return new DocumentRepository();
    }

    @Bean
    public DocumentService documentService() {
        return new DocumentService();
    }

    @Bean
    public FeedService feedService() {
        return new FeedService();
    }

    @Bean
    public FeedRepository feedRepository() {
        return new FeedRepository();
    }

    @Bean
    public FeedReaderService feedReaderService() {
        return new FeedReaderService();
    }

    @Bean
    public UserDocumentService userDocumentService() {
        return new UserDocumentService();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository();
    }

    @Bean
    public JedisPool getPool() throws URISyntaxException {
        String redis_url = System.getenv("REDIS_URL");
        if (redis_url == null) {
            redis_url = "redis://localhost:6379/0";
        }

        Objects.requireNonNull(redis_url, "Could not read REDIS_URL environment variable.");
        URI redisURI = new URI(redis_url);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxIdle(50);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        JedisPool jedisPool = new JedisPool(poolConfig, redisURI) {
            @Override
            public Jedis getResource() {
//                log.info("ENTER getResource");
                Jedis resource = super.getResource();
//                log.info("EXIT  getResource");
                return resource;
            }
        };
        return jedisPool;
    }

    @Bean
    public InboxService inboxService() {
        return new InboxService();
    }

    @Bean
    public InboxRepository inboxRepository() {
        return new InboxRepository();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public UserDocumentRepository userDocumentRepository() {
        return new UserDocumentRepository();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    public static void main(String[] args) {
        log.info("JAVA_OPTS: {}", System.getenv("JAVA_OPTS"));
        runtimeParameters();
        SpringApplication.run(Main.class, args);
    }

    public static void runtimeParameters() {
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        List<String> aList = bean.getInputArguments();

        for (int i = 0; i < aList.size(); i++) {
            log.info(aList.get( i ) );
        }
    }
}
