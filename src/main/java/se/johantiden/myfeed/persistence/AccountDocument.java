package se.johantiden.myfeed.persistence;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Objects;

@Entity
@NamedQueries({
    @NamedQuery(name = "AccountDocument.getReadyAccountDocumentIdsForAccount",
            query = "SELECT ud.id FROM AccountDocument ud WHERE" +
                    " ud.account.id = :accountId AND" +
                    " ud.read = false AND" +
                    " ud.document.subjectsParsed = true AND" +
                    " ud.document.tabsParsed = true"),
    @NamedQuery(name = "AccountDocument.findAllRead",
            query = "SELECT ud FROM AccountDocument ud WHERE" +
                    " ud.read = true")
})
public class AccountDocument extends BaseEntity {

    @ManyToOne(targetEntity = Account.class)
    private final Account account;
    @ManyToOne(targetEntity = Document.class)
    private final Document document;
    private boolean read;

    // JPA
    protected AccountDocument() {
        account = null;
        document = null;
    }

    public AccountDocument(Account account, Document document) {
        this.account = Objects.requireNonNull(account);
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

    public Account getAccount() {
        return account;
    }

    public Document getDocument() {
        return document;
    }
}
