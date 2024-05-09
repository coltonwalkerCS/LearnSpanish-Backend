package a.i.learn.spanish.com.learnspanishbackend.collection_feature;

import a.i.learn.spanish.com.learnspanishbackend.user_feature.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
// Resources:
// - https://docs.spring.io/spring-data/commons/reference/repositories/query-keywords-reference.html#appendix.query.method.subject
// - https://docs.spring.io/spring-data/commons/reference/repositories/query-methods-details.html
// - https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    List<Collection> findCollectionsByUser(User user);
    List<Collection> findTop6ByUserOrderByLastAccessedDesc(User user);

}
