package se.johantiden.myfeed;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.user.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

public class Saver {
    private static final Logger log = LoggerFactory.getLogger(Saver.class);
    public static final Path DOCUMENTS = new File("myfeed.documents.json").toPath();
    public static final Path USER_DOCUMENTS = new File("myfeed.userdocuments.json").toPath();


    @Autowired
    private Gson gson;

    public <T> Optional<T> load(Type type, Path path) {

        if (!Files.exists(path)) {
            return Optional.empty();
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            Object o = gson.fromJson(reader, type);
            T t = (T) o;
            return Optional.ofNullable(t);
        } catch (JsonSyntaxException | IOException e) {
            log.error("Failed to read file {}", path, e);
            return Optional.empty();
        }
    }

    public void save(Path path, Object o) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset(), CREATE, TRUNCATE_EXISTING, WRITE)) {
            gson.toJson(o, writer);
        } catch (IOException e) {
            log.error("Failed to save", e);
        }
    }
    public Optional<HashMap<Key<Document>, Document>> loadDocuments() {
        Type type = new HashMap<Key<Document>, Document>().getClass();
        return load(type, DOCUMENTS);
    }

    public Optional<Map<Key<User>, SortedSet<UserDocument>>> loadUserDocuments() {
        Type type = new HashMap<Key<User>, SortedSet<UserDocument>>().getClass();
        return load(type, USER_DOCUMENTS);
    }

    public void saveDocuments(Map<Key<Document>, Document> documentMap) {
        log.info("Saving documents...");
        save(DOCUMENTS, documentMap);
        log.info("Documents saved!");
    }

    public void saveUserDocuments(Map<Key<User>, SortedSet<UserDocument>> userDocumentMap) {
        log.info("Saving user documents...");
        save(USER_DOCUMENTS, userDocumentMap);
        log.info("User documents saved!");
    }
}
