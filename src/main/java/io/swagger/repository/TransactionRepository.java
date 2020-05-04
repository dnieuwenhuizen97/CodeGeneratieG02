package io.swagger.repository;

import io.swagger.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    //userRepository.findAll(new Sort("LENGTH(name)"));

    Page<Transaction> findByUserUserId(Integer userId, Pageable pageable);
}
