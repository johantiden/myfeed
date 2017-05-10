package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.controller.Subject;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.SubjectService;
import se.johantiden.myfeed.service.DocumentService;

import java.util.List;

@Component
public class SubjectTesterJob {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private SubjectService subjectService;

    @Scheduled(fixedRate = 1000)
    public void testSubjects() {

        List<Document> documents = documentService.find(d -> d.subject == null);

        documents.forEach(d -> {
            Subject subject = subjectService.getSubjectFor(d);
            d.subject = subject.getKey();
        });
    }
}
