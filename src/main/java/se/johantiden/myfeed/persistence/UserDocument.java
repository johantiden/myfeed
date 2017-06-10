package se.johantiden.myfeed.persistence;

import javax.persistence.Entity;
import java.util.Objects;



/*
* .filter(UserDocument::isUnread)
                            .filter(ud -> {
                                Optional<String> tab = documentService.find(ud.getDocumentKey()).flatMap(d -> Optional.ofNullable(d.tab));

                                return tab
                                        .map(t -> {
                                            boolean isBad = DocumentClassifier.BAD.equals(t);
                                            return !isBad;
//                                            boolean isSport = DocumentClassifier.SPORT.equals(t);
//                                            boolean isKultur = DocumentClassifier.CULTURE.equals(t);
//                                            return !isBad && !isSport && !isKultur;
                                        })
                                        .orElse(false);

                            })
* */

@Entity
public class UserDocument extends BaseEntity {

    private final User user;
    private final Document document;
    private boolean read;

    public UserDocument(User user, Document document) {
        this.user = Objects.requireNonNull(user);
        this.document = Objects.requireNonNull(document);
    }

    public final void setRead(boolean read) {
        this.read = read;
    }

    public final boolean isRead() {
        return read;
    }

    public final boolean isUnread() {
        return !read;
    }

    public User getUser() {
        return user;
    }

    public Document getDocument() {
        return document;
    }
}
