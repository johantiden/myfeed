package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentClassifier;
import se.johantiden.myfeed.persistence.Subject;
import se.johantiden.myfeed.service.DocumentService;

import java.util.List;

@Component
public class TabClassifierJob {

    private static final Logger log = LoggerFactory.getLogger(TabClassifierJob.class);
    @Autowired
    private DocumentService documentService;

    @Scheduled(fixedRate = 1000)
    public void testSubjects() {
        try {
            tryPop();
        } catch (RuntimeException e) {
            log.error("Could not find subjects", e);
        }
    }

    private void tryPop() {
        List<Document> documents = documentService.findDocumentsNotUnparsedSubjects();

        documents.forEach(d -> {
            DocumentClassifier.appendUrlFoldersAsCategory(d);
            List<Subject> subjects = DocumentClassifier.getSubjectFor(d);
            d.subjects.clear();
            d.subjects.addAll(subjects);
            String tab = DocumentClassifier.getTabFor(d);
            d.tabs.clear(); = tab;
        });
    }
}
