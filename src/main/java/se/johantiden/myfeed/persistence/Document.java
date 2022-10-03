package se.johantiden.myfeed.persistence;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import se.johantiden.myfeed.persistence.Subject.SubjectType;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

public class Document extends BaseEntity<Document> {
    public String title;
    public String text;
    private final String html;
    private final String pageUrl;
    public String imageUrl;
    public final Instant publishedDate;
    public Double score;
    public boolean isPaywalled;
    public String extra;
    public ArrayList<Video> videos = new ArrayList<>();
    private boolean subjectsParsed;
    private final Set<Subject> subjects;
    @Nonnull private final String feedName;
    @Nonnull private final String feedUrl;
    private boolean hidden;
    private boolean read;

    public Document(
            String title,
            String text,
            String html,
            @Nonnull String pageUrl,
            String imageUrl,
            Instant publishedDate,
            @Nonnull String feedName,
            String feedUrl) {

        title = cleanup(title);
        text = cleanup(text);

        verifyTextIsClean(text);
        verifyTextIsClean(title);
        this.title = title;
        this.text = text;
        this.html = html;
        this.pageUrl = Objects.requireNonNull(pageUrl);
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;
        this.feedName = Objects.requireNonNull(feedName);
        this.subjects = Sets.newHashSet(subjectForFeed(feedName));
        this.feedUrl = Objects.requireNonNull(feedUrl);
    }

    private static String cleanup(String text) {
        if (text == null) {
            return text;
        }
        return text
                .replaceAll("•", "")
                .trim();
    }

    private static void verifyTextIsClean(String text) {
        if (text == null) {
            return;
        }
        if (text.contains("<") || text.contains(">")) {
            throw new RuntimeException("Text cannot contain html objects! Text: " + text);
        }
        if (text.contains("•")) {
            throw new RuntimeException("Text cannot contain text styling! Text: " + text);
        }
    }

    public static String dateToShortString(Instant instant) {

        Instant now = Instant.now();

        long days = instant.until(now, ChronoUnit.DAYS);
        if (days >= 1L) {
            return days + "d";
        }

        long hours = instant.until(now, ChronoUnit.HOURS);
        if (hours >= 1L) {
            return hours + "h";
        }

        long minutes = instant.until(now, ChronoUnit.MINUTES);
        if (minutes >= 1L) {
            return minutes + "m";
        }

        long seconds = instant.until(now, ChronoUnit.SECONDS);
        if (seconds >= 1L) {
            return seconds + "s";
        }

        return "";
    }

    public static Subject subjectForFeed(String feedName) {
        Predicate<Document> predicate = d -> d.feedName.equals(feedName);
        ArrayList<Subject> parents = Lists.newArrayList(Subject.ALL);
        return new Subject(parents, feedName, SubjectType.FEED, predicate, false, false, true);
    }

    public boolean isSubjectsParsed() {
        return subjectsParsed;
    }

    public void setSubjectsParsed(boolean subjectsParsed) {
        this.subjectsParsed = subjectsParsed;
    }

    public String getPublishedShortString() {
        return dateToShortString(publishedDate);
    }

    public Instant getPublishedDate() {
        return publishedDate;
    }

    public Double getScore() {
        return score;
    }

    public ImmutableSet<Subject> getSubjects() {
        return ImmutableSet.copyOf(subjects);
    }

    public void addSubjects(Set<Subject> subjects) {
        this.subjects.addAll(subjects);
    }

    public String getFeedName() {
        return feedName;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    @javax.annotation.Nonnull
    public String getPageUrl() {
        return pageUrl;
    }

    public String getHtml() {
        return html;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                '}';
    }

    public boolean isNotHidden() {
        return !hidden;
    }
}
