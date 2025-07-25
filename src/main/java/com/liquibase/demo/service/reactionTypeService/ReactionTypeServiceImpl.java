//package com.liquibase.demo.service.reactionTypeService;
//
//import com.liquibase.demo.dto.ReactionTypeRequestDTO;
//import com.liquibase.demo.dto.ReactionTypeResponseDTO;
//import com.liquibase.demo.model.ReactionType;
//import com.liquibase.demo.repository.ReactionTypeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class ReactionTypeServiceImpl implements ReactionTypeService {
//    @Autowired
//    private ReactionTypeRepository reactionTypeRepository;
//
//    private ReactionTypeResponseDTO mapToDTO(ReactionType entity) {
//        ReactionTypeResponseDTO dto = new ReactionTypeResponseDTO();
//        dto.setId(entity.getId());
//        dto.setReactionType(entity.getReactionType());
//        dto.setCreatedAt(entity.getCreatedAt());
//        dto.setUpdatedAt(entity.getUpdatedAt());
//        return dto;
//    }
//
//    @Override
//    public ReactionTypeResponseDTO createReactionType(ReactionTypeRequestDTO requestDTO) {
//        ReactionType reactionType = new ReactionType();
//        reactionType.setReactionType(requestDTO.getReactionType());
//        reactionType.setCreatedAt(LocalDateTime.now());
//        return mapToDTO(reactionTypeRepository.save(reactionType));
//    }
//
//    @Override
//    public List<ReactionTypeResponseDTO> getAllReactionTypes() {
//        return reactionTypeRepository.findAll()
//                .stream()
//                .filter(rt -> rt.getDeletedAt() == null)
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public ReactionTypeResponseDTO getReactionTypeById(Long id) {
//        ReactionType rt = reactionTypeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Reaction Type not found"));
//        return mapToDTO(rt);
//    }
//
//    @Override
//    public ReactionTypeResponseDTO updateReactionType(Long id, ReactionTypeRequestDTO requestDTO) {
//        ReactionType existing = reactionTypeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Reaction Type not found"));
//        existing.setReactionType(requestDTO.getReactionType());
//        existing.setUpdatedAt(LocalDateTime.now());
//        return mapToDTO(reactionTypeRepository.save(existing));
//    }
//
//    @Override
//    public void deleteReactionType(Long id) {
//        ReactionType existing = reactionTypeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Reaction Type not found"));
//        existing.setDeletedAt(LocalDateTime.now());
//        reactionTypeRepository.save(existing);
//    }
//}
