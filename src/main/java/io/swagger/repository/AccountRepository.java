package io.swagger.repository;

import io.swagger.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account,String> {

    //@Query("SELECT a FROM Account a WHERE a.iban =:iban")
    //Account findAccountByIban(@Param("iban") String iban);
}
