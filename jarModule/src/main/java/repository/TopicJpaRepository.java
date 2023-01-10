package repository;

import entity.Post;
import entity.Topic;
import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;


public interface TopicJpaRepository extends JpaRepository<Topic, Long> {

    Topic findByNameTopic(String nameTopic);

    @Query("SELECT t FROM Post t where t.topic.id = : idTopic and t.user.id = :idUser")
    Set<Post> getAllPost(@Param("idUser") long idUser);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.topic  where u.id = :id")
    User getListTopic(@Param("id") long id);

    @Query("SELECT t FROM Topic t LEFT JOIN FETCH t.users  where t.id = :id")
    Topic getListUser(@Param("id") long id);
}
