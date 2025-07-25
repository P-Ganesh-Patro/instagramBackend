package com.liquibase.demo.controller;


import com.liquibase.demo.dto.GroupPostRequestDTO;
import com.liquibase.demo.dto.GroupPostResponseDTO;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.service.groupPostService.GroupPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-posts")
public class GroupPostController {
    @Autowired
    private GroupPostService groupPostService;

    @PostMapping
    public ResponseEntity<APIResponse<GroupPostResponseDTO>> createGroupPost(@RequestBody GroupPostRequestDTO requestDTO) {

        GroupPostResponseDTO groupPostResponseDTO = groupPostService.createGroupPost(requestDTO);
        APIResponse<GroupPostResponseDTO> response = new APIResponse<>(
                "post posted successfully",
                HttpStatus.CREATED,
                groupPostResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<APIResponse<List<GroupPostResponseDTO>>> getPostsByGroup(@PathVariable Long groupId) {
        List<GroupPostResponseDTO> groupPostResponseDTO = groupPostService.getGroupPostsByGroupId(groupId);
        APIResponse<List<GroupPostResponseDTO>> response = new APIResponse<>(
                "Group Fetch successfully",
                HttpStatus.OK,
                groupPostResponseDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        groupPostService.deleteGroupPost(id);
        return ResponseEntity.ok("Group post deleted successfully");
    }
}
