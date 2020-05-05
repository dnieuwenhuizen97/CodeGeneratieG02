package io.swagger.repository;

import io.swagger.model.Account;
import io.swagger.model.AuthToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account,Long> {

    @Query("SELECT a FROM Account a WHERE a.iban =:iban")
    Account findAccountById(@Param("iban") String iban);
}
