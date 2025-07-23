package com.liquibase.demo.service.groupService;

import com.liquibase.demo.dto.GroupRequestDTO;
import com.liquibase.demo.dto.GroupResponseDTO;

import java.util.List;

public interface GroupService {
    GroupResponseDTO createGroup(GroupRequestDTO requestDTO);
    List<GroupResponseDTO> getAllGroups();
    GroupResponseDTO getGroupById(Long id);
    void deleteGroup(Long id);
}
