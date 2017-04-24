package se.johantiden.myfeed.persistence.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

public class BaseSaver {
    private static final Logger log = LoggerFactory.getLogger(BaseSaver.class);
    public static final Path DOCUMENTS = new File("myfeed.documents.json").toPath();
    public static final Path USER_DOCUMENTS = new File("myfeed.userdocuments.json").toPath();

    public <T> Optional<T> load(Path path) {

        if (!Files.exists(path)) {
            return Optional.empty();
        }

        try (InputStream is = Files.newInputStream(path, READ);
             ObjectInputStream ios = new ObjectInputStream(is)) {
            Object o = ios.readObject();
            T t = (T) o;
            return Optional.ofNullable(t);
        } catch (IOException e) {
            log.error("Failed to read file {}", path, e);
            return Optional.empty();
        } catch (ClassNotFoundException e) {
            log.error("Failed to read file {}", e);
            return Optional.empty();
        }
    }

    public static void save(Path path, Object o) {
        try (OutputStream os = Files.newOutputStream(path, CREATE, TRUNCATE_EXISTING, WRITE);
             ObjectOutputStream oos = new ObjectOutputStream(os);
        ) {
            oos.writeObject(o);
            oos.close();
        } catch (IOException e) {
            log.error("Failed to save", e);
        }
    }
}
