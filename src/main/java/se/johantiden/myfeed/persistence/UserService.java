package se.johantiden.myfeed.persistence;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> find(String username) {
        return Optional.ofNullable(userRepository.findOneByUsername(username));
    }

    public User create(String username) {
        User user = new User(username);
        userRepository.save(user);
        return user;
    }
}
