package com.pvt.repository;


import com.pvt.entity.Topic;
import com.pvt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    User findByUserName(String userName);

    @Query("SELECT t FROM Topic t LEFT JOIN FETCH t.users  where t.id = :id")
    Topic getListUsersWhereIdTopic(@Param("id") long id);
}
