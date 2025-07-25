package com.liquibase.demo.service.groupService;

import com.liquibase.demo.dto.GroupDeletedResponse;
import com.liquibase.demo.dto.GroupRequestDTO;
import com.liquibase.demo.dto.GroupResponseDTO;
import com.liquibase.demo.dto.GroupUpdateRequestDTO;

import java.util.List;

public interface GroupService {
    GroupResponseDTO createGroup(GroupRequestDTO requestDTO);

    List<GroupResponseDTO> getAllGroups();

    GroupResponseDTO getGroupById(Long id);

    GroupDeletedResponse deleteGroup(Long id);

    GroupResponseDTO updateGroupNameById(GroupUpdateRequestDTO groupRequestDTO, Long id);

}
