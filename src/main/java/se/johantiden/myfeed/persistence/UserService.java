package se.johantiden.myfeed.persistence;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    public Collection<User> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll());
    }

    public Optional<User> find(String username) {
        return Optional.ofNullable(userRepository.findOneByUsername(username));
    }

    public User create(String username) {
        log.info("Creating user '{}'", username);
        User user = new User(username);
        userRepository.save(user);
        return user;
    }
}
