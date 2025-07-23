package com.liquibase.demo.controller;


import com.liquibase.demo.dto.GroupPostRequestDTO;
import com.liquibase.demo.dto.GroupPostResponseDTO;
import com.liquibase.demo.service.groupPostService.GroupPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-posts")
public class GroupPostController {
    @Autowired
    private GroupPostService groupPostService;

    @PostMapping
    public ResponseEntity<GroupPostResponseDTO> createGroupPost(@RequestBody GroupPostRequestDTO requestDTO) {
        return ResponseEntity.ok(groupPostService.createGroupPost(requestDTO));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupPostResponseDTO>> getPostsByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupPostService.getGroupPostsByGroupId(groupId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        groupPostService.deleteGroupPost(id);
        return ResponseEntity.ok("Group post deleted successfully");
    }
}
