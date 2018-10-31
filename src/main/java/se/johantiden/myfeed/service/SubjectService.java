package se.johantiden.myfeed.service;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.SubjectClassifier;
import se.johantiden.myfeed.persistence.Subject;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SubjectService {

    private final Set<Subject> subjects;
    private final DocumentService documentService;

    public SubjectService(DocumentService documentService) {
        this.subjects = SubjectClassifier.getSubjects();
        this.documentService = Objects.requireNonNull(documentService);
    }

    public void parseSubjectsFor(Document document) {

        document.setSubjectsParsed(true);

        Set<Subject> matchingRules = subjects.stream()
                .filter(r -> r.isMatch(document))
                .collect(Collectors.toSet());

        boolean hidden = matchingRules.stream().anyMatch(Subject::isHideDocument);
        document.setHidden(hidden);
        if (hidden) {
            documentService.setRead(document.getId());
        }

        document.addSubjects(matchingRules);

        documentService.put(document);
    }
}
