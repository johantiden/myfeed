package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentClassifier;
import se.johantiden.myfeed.service.DocumentService;

import java.util.List;
import java.util.Set;

@Component
public class SubjectClassifierJob {

    private static final Logger log = LoggerFactory.getLogger(SubjectClassifierJob.class);
    @Autowired
    private DocumentService documentService;

    @Scheduled(fixedRate = 1000)
    public void testSubjects() {
//        try {
            tryPop();
//        } catch (RuntimeException e) {
//            log.error("Could not find subjects", e);
//        }
    }

    private void tryPop() {
        List<Document> documents = documentService.findDocumentsNotParsedSubjects();

        documents.forEach(d -> {
            d.setSubjectsParsed(true);
            DocumentClassifier.appendUrlFoldersAsCategories(d);
            Set<String> subjects = DocumentClassifier.getSubjectFor(d);
            d.getSubjects().clear();
            d.getSubjects().addAll(subjects);
            documentService.put(d);
        });
    }
}
