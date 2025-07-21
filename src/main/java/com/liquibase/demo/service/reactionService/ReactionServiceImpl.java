//package com.liquibase.demo.service.reactionService;
//
//import com.liquibase.demo.dto.ReactionDTO;
//import com.liquibase.demo.model.Reaction;
//import com.liquibase.demo.model.ReactionType;
//import com.liquibase.demo.repository.ReactionRepository;
//import com.liquibase.demo.repository.ReactionTypeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ReactionServiceImpl implements ReactionService{
//    @Autowired
//    private ReactionRepository reactionRepository;
//
//    @Autowired
//    private ReactionTypeRepository reactionTypeRepository;
//
//    @Override
//    public Reaction saveReaction(ReactionDTO dto) {
//        Reaction reaction = new Reaction();
//        reaction.setUserId(dto.getUserId());
//        reaction.setCommentId(dto.getCommentId());
//        reaction.setReactedType(dto.getReactedType());
//
//        ReactionType type = reactionTypeRepository.findById(dto.getReactionTypeId())
//                .orElseThrow(() -> new RuntimeException("Invalid ReactionType ID"));
//
//        reaction.setReactedType(type);
//        reaction.setCreatedAt(LocalDateTime.now());
//
//        return reactionRepository.save(reaction);
//    }
//
//    @Override
//    public List<Reaction> getAllReactions() {
//        return reactionRepository.findAll()
//                .stream()
//                .filter(r -> r.getDeletedAt() == null)
//                .toList();
//    }
//
//    @Override
//    public Optional<Reaction> getReactionById(Long id) {
//        return reactionRepository.findById(id)
//                .filter(r -> r.getDeletedAt() == null);
//    }
//
//    @Override
//    public Reaction updateReaction(Long id, ReactionDTO dto) {
//        return reactionRepository.findById(id)
//                .filter(r -> r.getDeletedAt() == null)
//                .map(reaction -> {
//                    reaction.setReactedType(dto.getReactedType());
//                    reaction.setUpdatedAt(LocalDateTime.now());
//                    reaction.setUserId(dto.getUserId());
//                    reaction.setCommentId(dto.getCommentId());
//
//                    ReactionType type = reactionTypeRepository.findById(dto.getReactionTypeId())
//                            .orElseThrow(() -> new RuntimeException("Invalid ReactionType ID"));
//
//                    reaction.setReactionType(type);
//
//                    return reactionRepository.save(reaction);
//                })
//                .orElseThrow(() -> new RuntimeException("Reaction not found"));
//    }
//
//    @Override
//    public void softDeleteReaction(Long id) {
//        reactionRepository.findById(id).ifPresent(reaction -> {
//            reaction.setDeletedAt(LocalDateTime.now());
//            reactionRepository.save(reaction);
//        });
//    }
//}
