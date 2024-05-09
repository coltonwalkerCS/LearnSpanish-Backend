package a.i.learn.spanish.com.learnspanishbackend.user_feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> payload){
        // Assert that the username is not in the system
        Optional<User> userOptional = userRepository.findUserByUsername(payload.get("username"));
        if (userOptional.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("User already exist with username: " + payload.get("username"));
        } else {
            return new ResponseEntity<>(userService.createUser(payload.get("username"), payload.get("password")), HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authLogin(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        Optional<User> userOptional = userService.getUserByUsernameAndPassword(username, password);

        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
