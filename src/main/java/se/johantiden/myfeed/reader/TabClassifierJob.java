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
public class TabClassifierJob {

    private static final Logger log = LoggerFactory.getLogger(TabClassifierJob.class);
    @Autowired
    private DocumentService documentService;

    @Scheduled(fixedRate = 1000)
    public void testTabs() {
//        try {
            tryPop();
//        } catch (RuntimeException e) {
//            log.error("Could not find tabs", e);
//        }
    }

    private void tryPop() {
        List<Document> documents = documentService.findDocumentsNotParsedTabs();

        documents.stream()
                .filter(Document::isSubjectsParsed)
                .forEach(d -> {
                    d.setTabsParsed(true);
                    DocumentClassifier.appendUrlFoldersAsCategories(d);
                    Set<String> tabs = DocumentClassifier.getTabsFor(d);
                    d.getTabs().clear();
                    d.getTabs().addAll(tabs);
                    documentService.put(d);
                });
    }
}