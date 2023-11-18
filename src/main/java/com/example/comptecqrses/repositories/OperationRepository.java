package com.example.comptecqrses.repositories;

import com.example.comptecqrses.entities.Account;
import com.example.comptecqrses.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
}
