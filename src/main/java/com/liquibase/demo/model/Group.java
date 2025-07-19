package com.liquibase.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created_At;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


}
