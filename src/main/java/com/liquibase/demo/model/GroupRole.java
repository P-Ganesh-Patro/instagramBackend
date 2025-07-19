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


}
