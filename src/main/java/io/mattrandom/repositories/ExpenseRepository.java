package io.mattrandom.repositories;

import io.mattrandom.repositories.entities.ExpenseEntity;
import io.mattrandom.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    List<ExpenseEntity> findByUserEntity(UserEntity userEntity);

    @Query("SELECT e FROM ExpenseEntity e WHERE e.userEntity = :user AND e.expenseDate BETWEEN :expenseDateFrom AND :expenseDateTo")
    List<ExpenseEntity> findByExpenseDateBetween(UserEntity user, LocalDateTime expenseDateFrom, LocalDateTime expenseDateTo);
}