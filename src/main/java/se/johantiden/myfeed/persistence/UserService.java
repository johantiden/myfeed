package se.johantiden.myfeed.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.persistence.user.UserRepository;

import java.util.Collection;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
