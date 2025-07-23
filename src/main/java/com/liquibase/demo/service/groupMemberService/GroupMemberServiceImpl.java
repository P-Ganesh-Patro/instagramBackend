package com.liquibase.demo.service.groupMemberService;

import com.liquibase.demo.dto.GroupMemberRequestDTO;
import com.liquibase.demo.dto.GroupMemberResponseDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Group;
import com.liquibase.demo.model.GroupMember;
import com.liquibase.demo.model.GroupRole;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.GroupMemberRepository;
import com.liquibase.demo.repository.GroupRepository;
import com.liquibase.demo.repository.GroupRoleRepository;
import com.liquibase.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GroupMemberServiceImpl implements GroupMemberService{
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRoleRepository groupRoleRepository;


    @Override
    public GroupMemberResponseDTO addGroupMember(GroupMemberRequestDTO requestDTO) {
        Group group = groupRepository.findById(requestDTO.getGroupId())
                .orElseThrow(() -> new UserNotFoundException("Group not found"));

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        GroupRole role = groupRoleRepository.findById(requestDTO.getRoleId())
                .orElseThrow(() -> new UserNotFoundException("Group role not found"));

        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUser(user);
        member.setRole(role);
        member.setCreatedAt(LocalDateTime.now());

        GroupMember saved = groupMemberRepository.save(member);

        return toDTO(saved);
    }

    @Override
    public List<GroupMemberResponseDTO> getGroupMembersByGroupId(Long groupId) {
        List<GroupMember> members = groupMemberRepository.findByGroupId(groupId);
        return members.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public void removeGroupMember(Long id) {
        GroupMember member = groupMemberRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Group member not found"));
        groupMemberRepository.delete(member);
    }

    private GroupMemberResponseDTO toDTO(GroupMember member) {
        GroupMemberResponseDTO dto = new GroupMemberResponseDTO();
        dto.setId(member.getId());
        dto.setUserName(member.getUser().getUserName());
        dto.setGroupName(member.getGroup().getGroupName());
        dto.setRoleName(member.getRole().getGroupRoleType().name());
        dto.setCreatedAt(member.getCreatedAt());
        return dto;
    }
}
