package com.liquibase.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "groups")
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

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

    @OneToMany(mappedBy = "group")
    private List<GroupMember> members;

    @OneToMany(mappedBy = "group")
    private List<GroupPost> groupPosts;


}
