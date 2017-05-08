package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.controller.Subject;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.SubjectRepository;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.UserDocumentService;

import java.util.List;
import java.util.function.Predicate;

@Component
public class SubjectTesterJob {

    private static final Logger log = LoggerFactory.getLogger(SubjectTesterJob.class);

    @Autowired
    private UserDocumentService userDocumentService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectRepository subjectRepository;

    @Scheduled(fixedRate = 1000)
    public void testSubjects() {

        List<Subject> allSubjects = subjectRepository.getAllSubjects();

        allSubjects.forEach(this::process);
    }

    private void process(Subject subject) {

        Key<Subject> key = subject.getKey();

        List<Document> document = documentService.find(hasNotTestedSubject(key));
        document.forEach(d -> {
            if (subject.test(d)) {
                log.info("Adding subject: " + subject.getTitle());
                d.subjects.add(key);
            } else {
                d.nonMatchedSubjects.add(key);
            }
        });
    }

    private static Predicate<Document> hasNotTestedSubject(Key<Subject> key) {
        return d -> !d.subjects.contains(key) && !d.nonMatchedSubjects.contains(key);
    }
}
