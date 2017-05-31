package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

public class DB implements Serializable {

    private static final long serialVersionUID = 2070839941803113154L;

    public final Map<Key<Document>, Document> documents = new HashMap<>();
    public final HashMap<Username, SortedSet<UserDocument>> userDocuments = new HashMap<>();

}
