package com.liquibase.demo.service.groupMemberService;

import com.liquibase.demo.dto.GroupMemberRequestDTO;
import com.liquibase.demo.dto.GroupMemberResponseDTO;

import java.util.List;

public interface GroupMemberService {
    GroupMemberResponseDTO addGroupMember(GroupMemberRequestDTO requestDTO);
    List<GroupMemberResponseDTO> getGroupMembersByGroupId(Long groupId);
    void removeGroupMember(Long id);
}
