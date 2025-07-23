package com.liquibase.demo.controller;


import com.liquibase.demo.dto.GroupMemberRequestDTO;
import com.liquibase.demo.dto.GroupMemberResponseDTO;
import com.liquibase.demo.service.groupMemberService.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-members")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    @PostMapping
    public ResponseEntity<GroupMemberResponseDTO> addGroupMember(@RequestBody GroupMemberRequestDTO requestDTO) {
        return ResponseEntity.ok(groupMemberService.addGroupMember(requestDTO));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupMemberResponseDTO>> getGroupMembers(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupMemberService.getGroupMembersByGroupId(groupId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroupMember(@PathVariable Long id) {
        groupMemberService.removeGroupMember(id);
        return ResponseEntity.ok("Group member removed successfully");
    }
}
