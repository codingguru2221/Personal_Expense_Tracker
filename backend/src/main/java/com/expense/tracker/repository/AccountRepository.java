package com.expense.tracker.repository;

import com.expense.tracker.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.user.id = :userId")
    List<Account> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT a FROM Account a JOIN FETCH a.bank WHERE a.user.id = :userId")
    List<Account> findByUserIdWithBank(@Param("userId") Long userId);
}