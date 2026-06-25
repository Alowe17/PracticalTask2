package com.example.demo.repository.task;

import com.example.demo.model.entity.account.Account;
import com.example.demo.model.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByAccount(Account account);
    boolean existsByIdAndAccount(Long id, Account account);
    Optional<Task> findByIdAndAccount(Long id, Account account);
}