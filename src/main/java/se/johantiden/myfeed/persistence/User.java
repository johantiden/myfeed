package se.johantiden.myfeed.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends BaseEntity {

    private final String username;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "feed_user", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "feed_id",
                    nullable = false, updatable = false) })
    private final List<Feed> feeds;


    public User(String username) {
        this.username = username;
        feeds = new ArrayList<>();
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public String getUsername() {
        return username;
    }
}
