package com.liquibase.demo.repository;

import com.liquibase.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u.username FROM users u WHERE u.username = :username", nativeQuery = true)
    String findByUserName(@Param("username") String userName);

    @Query(value = "SELECT u.email FROM users u WHERE u.email = :email", nativeQuery = true)
    String findByUserEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE (username = :userNameOrEmail or email = :userNameOrEmail) AND password = :password", nativeQuery = true)
    User findByUserNameOrEmailAndPassword(@Param("userNameOrEmail") String userNameOrEmail, @Param("password") String password);


}
