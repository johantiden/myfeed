package se.johantiden.myfeed.reader;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.SubjectService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SubjectClassifierJob {

    private static final Logger log = LoggerFactory.getLogger(SubjectClassifierJob.class);
    private final DocumentService documentService;
    private final SubjectService subjectService;
    private final ScheduledExecutorService executor;

    public SubjectClassifierJob(DocumentService documentService, SubjectService subjectService, ScheduledExecutorService executor) {
        this.documentService = Objects.requireNonNull(documentService);
        this.subjectService = Objects.requireNonNull(subjectService);
        this.executor = Objects.requireNonNull(executor);
    }

    private static List<String> parseUrlFolders(String pageUrl) {
        StringTokenizer stringTokenizer = new StringTokenizer(pageUrl);

        List<String> folders = Lists.newArrayList();

        while (stringTokenizer.hasMoreTokens()) {
            folders.add(stringTokenizer.nextToken("/"));
        }
        return folders;
    }

    private static boolean urlFilter(String string) {
        boolean matches = string.matches("([a-zA-Z]{2,})|([a-zA-Z]{2,}\\-[a-zA-Z]{2,})");
        return matches;
    }

    @PostConstruct
    public void start() {
        executor.scheduleWithFixedDelay(this::testSubjects, 1, 1, TimeUnit.SECONDS);
        log.info("Started SubjectClassifierJob");
    }

    public void testSubjects() {
        try {
            tryPop();
        } catch (RuntimeException e) {
            log.error("Could not find subjects", e);
        }
    }

    private void tryPop() {
        Set<Document> documents = documentService.findDocumentsNotParsedSubjects();
        documents.forEach(subjectService::parseSubjectsFor);
    }
}
