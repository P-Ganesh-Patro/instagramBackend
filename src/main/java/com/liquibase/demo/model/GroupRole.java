package com.liquibase.demo.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_role")
public class GroupRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_role_type", nullable = false)
    private GroupRoleType groupRoleType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupRoleType getGroupRoleType() {
        return groupRoleType;
    }

    public void setGroupRoleType(GroupRoleType groupRoleType) {
        this.groupRoleType = groupRoleType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
