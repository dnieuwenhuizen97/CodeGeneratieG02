package io.swagger.repository;

import io.swagger.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("Select u FROM User u WHERE u.user_id = ?1")
    User getUserById(Integer id);

}
