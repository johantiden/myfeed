package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentClassifier;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.SubjectService;

import java.util.List;

@Component
public class SubjectClassifierJob {

    private static final Logger log = LoggerFactory.getLogger(SubjectClassifierJob.class);
    @Autowired
    private DocumentService documentService;
    @Autowired
    private SubjectService subjectService;

    @Scheduled(fixedRate = 1000)
    public void testSubjects() {
        try {
            tryPop();
        } catch (RuntimeException e) {
            log.error("Could not find subjects", e);
        }
    }

    private void tryPop() {
        List<Document> documents = documentService.findDocumentsNotParsedSubjects();

        documents.forEach(d -> {
            DocumentClassifier.appendUrlFoldersAsCategories(d);
            subjectService.parseSubjectsFor(d);
        });
    }
}
