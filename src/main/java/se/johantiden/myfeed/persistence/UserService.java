package se.johantiden.myfeed.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.user.UserRepository;

import java.util.Collection;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Collection<Username> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
