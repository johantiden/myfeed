package se.johantiden.myfeed.reader;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.classification.DocumentMatcher;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.SubjectService;

import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Component
public class SubjectClassifierJob {

    private static final Logger log = LoggerFactory.getLogger(SubjectClassifierJob.class);
    @Autowired
    private DocumentService documentService;
    @Autowired
    private SubjectService subjectService;

    public static void appendUrlFoldersAsCategories(Document document) {

        DocumentMatcher m = new DocumentMatcher(document);

        if (!m.isFromFeed("HackerNews")) {
            List<String> folders = parseUrlFolders(document.getPageUrl()).stream()
                                   .filter(SubjectClassifierJob::urlFilter)
                                   .collect(Collectors.toList());

            folders.stream()
                .filter(f -> !m.anyCategoryEquals(f))
                .filter(f -> !f.equals("artikel"))
                .filter(f -> !f.equals("comments"))
                .filter(f -> !f.equals("worldNews"))
                .filter(f -> !f.equals("Reuters"))
                .filter(f -> !f.equals("story"))
                .map(StringUtils::capitalize)
                .forEach(document.getSourceCategories()::add);
        }
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
            appendUrlFoldersAsCategories(d);
            subjectService.parseSubjectsFor(d);
        });
    }
}
