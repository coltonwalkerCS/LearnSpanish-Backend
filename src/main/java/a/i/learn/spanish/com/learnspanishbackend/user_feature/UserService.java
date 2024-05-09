package a.i.learn.spanish.com.learnspanishbackend.user_feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    // Private final: Increases restrictions by making variable not only
    // hidden but also immutable (once declared not able to be changed).
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // Gets the user by id, if it exists
    public Optional<User> getUser(Long id) { return userRepository.findById(id); }

    // Creates a user given the username and password
    public User createUser(String username, String password) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        return userRepository.save(new User(username, encodedPassword));
    }

    // Gets a user if it exists by username and checks if the password matches in the db
    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent() && bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

}
