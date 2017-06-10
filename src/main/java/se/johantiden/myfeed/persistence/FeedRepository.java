package se.johantiden.myfeed.persistence;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface FeedRepository extends CrudRepository<Feed, Long> {
    List<Feed> findAllInvalidatedFeeds();
}
