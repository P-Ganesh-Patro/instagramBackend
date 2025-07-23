package com.liquibase.demo.service.groupPostService;


import com.liquibase.demo.dto.GroupPostRequestDTO;
import com.liquibase.demo.dto.GroupPostResponseDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.Group;
import com.liquibase.demo.model.GroupPost;
import com.liquibase.demo.model.Post;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.GroupPostRepository;
import com.liquibase.demo.repository.GroupRepository;
import com.liquibase.demo.repository.PostRepository;
import com.liquibase.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupPostServiceImpl implements GroupPostService {
    @Autowired
    private GroupPostRepository groupPostRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public GroupPostResponseDTO createGroupPost(GroupPostRequestDTO dto) {
        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new UserNotFoundException("Group not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new UserNotFoundException("Post not found"));

        GroupPost groupPost = new GroupPost();
        groupPost.setGroup(group);
        groupPost.setUser(user);
        groupPost.setPost(post);
        groupPost.setContent(dto.getContent());
        groupPost.setCreated_At(LocalDateTime.now());

        GroupPost saved = groupPostRepository.save(groupPost);
        return toDTO(saved);
    }


    @Override
    public List<GroupPostResponseDTO> getGroupPostsByGroupId(Long groupId) {
        List<GroupPost> posts = groupPostRepository.findByGroupId(groupId);
        return posts.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteGroupPost(Long id) {
        GroupPost post = groupPostRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Group post not found"));
        groupPostRepository.delete(post);
    }

    private GroupPostResponseDTO toDTO(GroupPost post) {
        GroupPostResponseDTO dto = new GroupPostResponseDTO();
        dto.setId(post.getId());
        dto.setGroupName(post.getGroup().getGroupName());
        dto.setUserName(post.getUser().getUserName());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreated_At());
        return dto;
    }
}
