package a.i.learn.spanish.com.learnspanishbackend.user_feature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

//    Optional<User> findUserByUsernameAndPassword(String username, String password);

}

