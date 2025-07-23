package com.liquibase.demo.service.groupPostService;

import com.liquibase.demo.dto.GroupPostRequestDTO;
import com.liquibase.demo.dto.GroupPostResponseDTO;

import java.util.List;

public interface GroupPostService {
    GroupPostResponseDTO createGroupPost(GroupPostRequestDTO requestDTO);
    List<GroupPostResponseDTO> getGroupPostsByGroupId(Long groupId);
    void deleteGroupPost(Long id);
}
