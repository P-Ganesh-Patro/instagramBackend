package com.liquibase.demo.repository;

import com.liquibase.demo.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {


    List<GroupMember> findByGroupId(Long groupId);
}
