package com.liquibase.demo.controller;


import com.liquibase.demo.dto.GroupMemberRequestDTO;
import com.liquibase.demo.dto.GroupMemberResponseDTO;
import com.liquibase.demo.dto.GroupMemberUpdateRequestDTO;
import com.liquibase.demo.dto.GroupResponseDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.service.groupMemberService.GroupMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-members")
@Tag(name = "Group Members")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    @Operation(summary = "add group members")
    @PostMapping
    public ResponseEntity<APIResponse<GroupMemberResponseDTO>> addGroupMember(@RequestBody GroupMemberRequestDTO requestDTO) {
        GroupMemberResponseDTO groupMemberRequestDTO = groupMemberService.addGroupMember(requestDTO);
        if (groupMemberRequestDTO == null) {
            APIResponse<GroupMemberResponseDTO> response = new APIResponse<>(
                    "Not found",
                    HttpStatus.NOT_FOUND,
                    null
            );
        }
        APIResponse<GroupMemberResponseDTO> response = new APIResponse<>(
                "Group member added successfully",
                HttpStatus.CREATED,
                groupMemberRequestDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "update group members")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<GroupMemberResponseDTO>> updateGroupMember(@RequestBody GroupMemberUpdateRequestDTO groupMemberRequestDTO, @PathVariable Long id) {
        GroupMemberResponseDTO groupMemberResponseDTO = groupMemberService.updateGroupMember(groupMemberRequestDTO, id);
        APIResponse<GroupMemberResponseDTO> groupMemberResponseDTOAPIResponse = new APIResponse<>(
                "Group member update success",
                HttpStatus.OK,
                groupMemberResponseDTO
        );
        return new ResponseEntity<>(groupMemberResponseDTOAPIResponse, HttpStatus.OK);


    }


    @Operation(summary = "fetch group members")
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupMemberResponseDTO>> getGroupMembers(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupMemberService.getGroupMembersByGroupId(groupId));
    }


    @Operation(summary = "delete group members by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroupMember(@PathVariable Long id) {
        groupMemberService.removeGroupMember(id);
        return ResponseEntity.ok("Group member removed successfully");
    }
}
