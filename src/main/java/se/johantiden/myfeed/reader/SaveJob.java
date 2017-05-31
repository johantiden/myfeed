package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.DB;
import se.johantiden.myfeed.persistence.file.BaseSaver;

@Component
public class SaveJob {

    private static final Logger log = LoggerFactory.getLogger(SaveJob.class);

    @Autowired
    private DB db;

    @Scheduled(fixedRate = 20*1000, initialDelay = 5*1000)
    public void saveToDisk() {
        BaseSaver.save(BaseSaver.DB, db);
        log.info("Saved All!");
    }
}
