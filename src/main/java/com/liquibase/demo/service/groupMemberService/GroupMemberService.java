package com.liquibase.demo.service.groupMemberService;

import com.liquibase.demo.dto.*;

import java.util.List;

public interface GroupMemberService {
    GroupMemberResponseDTO addGroupMember(GroupMemberRequestDTO requestDTO);

    List<GroupMemberResponseDTO> getGroupMembersByGroupId(Long groupId);

    void removeGroupMember(Long id);

    GroupMemberResponseDTO updateGroupMember(GroupMemberUpdateRequestDTO groupUpdateRequestDTO, Long id);

    GroupMemberResponseDTO getGroupMemberById(Long id);


}
