package io.swagger.repository;

import io.swagger.model.AuthToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends CrudRepository<AuthToken,Long> {
   // AuthToken findAuthTokenByToken (String authToken);

    @Query("SELECT t FROM AuthToken t WHERE t.authToken =:authToken")
    AuthToken findAuthTokenByToken(@Param("authToken") String authToken);
}
