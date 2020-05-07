package io.swagger.repository;

import io.swagger.model.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email =:email AND u.password = :password")
    User findUserByUserCredentials(@Param("email") String email,@Param("password") String password);

    @Query("SELECT u FROM User u WHERE u.email =:email")
    User findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.userId =:userId")
    User findUserById(@Param("userId") Integer userId);

}