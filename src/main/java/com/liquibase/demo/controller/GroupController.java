package com.liquibase.demo.controller;


import com.liquibase.demo.dto.GroupDeletedResponse;
import com.liquibase.demo.dto.GroupRequestDTO;
import com.liquibase.demo.dto.GroupResponseDTO;
import com.liquibase.demo.dto.GroupUpdateRequestDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Group;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.service.groupService.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;


    @PostMapping
    public ResponseEntity<APIResponse<GroupResponseDTO>> createGroup(@RequestBody GroupRequestDTO requestDTO) {

        GroupResponseDTO createdGroup = groupService.createGroup(requestDTO);
        APIResponse<GroupResponseDTO> response = new APIResponse<>(
                "Group Created successfully",
                HttpStatus.CREATED,
                createdGroup
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<GroupResponseDTO>>> getAllGroups() {
        List<GroupResponseDTO> allGroups = groupService.getAllGroups();
        APIResponse<List<GroupResponseDTO>> response = new APIResponse<>(
                "All group fetched successfully",
                HttpStatus.OK,
                allGroups
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<GroupResponseDTO>> getGroupById(@PathVariable Long id) {
        GroupResponseDTO groupResponseDTO = groupService.getGroupById(id);

        if (groupResponseDTO == null) {
            APIResponse<GroupResponseDTO> response = new APIResponse<>(
                    "No group found",
                    HttpStatus.NOT_FOUND,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
        APIResponse<GroupResponseDTO> response = new APIResponse<>(
                "Group Fetched Successfully",
                HttpStatus.OK,
                groupResponseDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<GroupResponseDTO>> updateGroupNameById(@PathVariable Long id, @RequestBody GroupUpdateRequestDTO groupRequestDTO) {
        GroupResponseDTO group = groupService.getGroupById(id);
        if (group == null) {
            throw new UserNotFoundException("group  not found");
        }
        GroupResponseDTO groupResponseDTO = groupService.updateGroupNameById(groupRequestDTO, id);

        APIResponse<GroupResponseDTO> response = new APIResponse<>(
                "Group Details Update successfully",
                HttpStatus.OK,
                groupResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<GroupDeletedResponse>> deleteGroup(@PathVariable Long id) {
        GroupDeletedResponse group = groupService.deleteGroup(id);
        APIResponse<GroupDeletedResponse> deletedResponseAPIResponse = new APIResponse<>(
                "Group Deleted Successfully",
                HttpStatus.OK,
                group
        );

        return new ResponseEntity<>(deletedResponseAPIResponse, HttpStatus.OK);


    }

}
