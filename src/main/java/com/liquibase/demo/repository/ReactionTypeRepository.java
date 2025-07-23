package com.liquibase.demo.repository;

import com.liquibase.demo.model.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionTypeRepository extends JpaRepository<ReactionType, Long> {

    ReactionType findByReactionType(Enum<?> reactionType);
}
