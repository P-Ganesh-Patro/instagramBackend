package com.liquibase.demo.service.groupService;


import com.liquibase.demo.dto.GroupRequestDTO;
import com.liquibase.demo.dto.GroupResponseDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Group;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.GroupRepository;
import com.liquibase.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public GroupResponseDTO createGroup(GroupRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Group group = new Group();
        group.setGroupName(requestDTO.getGroupName());
        group.setDescription(requestDTO.getDescription());
        group.setCreatedBy(user);
        group.setCreated_At(LocalDateTime.now());

        Group savedGroup = groupRepository.save(group);
        return mapToDTO(savedGroup);
    }

    @Override
    public List<GroupResponseDTO> getAllGroups() {
        return groupRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private GroupResponseDTO mapToDTO(Group group) {
        GroupResponseDTO dto = new GroupResponseDTO();
        dto.setId(group.getId());
        dto.setGroupName(group.getGroupName());
        dto.setDescription(group.getDescription());
        dto.setCreatedByName(group.getCreatedBy().getUserName());
        dto.setCreatedAt(group.getCreated_At());
        return dto;
    }

    @Override
    public GroupResponseDTO getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        return mapToDTO(group);
    }

    @Override
    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        groupRepository.delete(group);
    }
}
