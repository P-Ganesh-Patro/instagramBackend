package com.liquibase.demo.repository;

import com.liquibase.demo.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

}
