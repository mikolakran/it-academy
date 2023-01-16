package com.pvt.repository;


import com.pvt.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
    @Query("SELECT t FROM Post t where t.topic.id = :idTopic and t.user.id = :idUser")
     Set<Post> getListByIdUserPost(@Param("idTopic") long idTopic,@Param("idUser") long idUser);
}
