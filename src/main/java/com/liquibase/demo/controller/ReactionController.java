//package com.liquibase.demo.controller;
//
//
//import com.liquibase.demo.dto.ReactionDTO;
//import com.liquibase.demo.dto.ReactionResponseDTO;
//import com.liquibase.demo.response.APIResponse;
//import com.liquibase.demo.service.reactionService.ReactionServiceImpl;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/reactions")
//@AllArgsConstructor
//public class ReactionController {
//
//    private final ReactionServiceImpl reactionService;
//
//    @PostMapping
//    public ResponseEntity<ReactionResponseDTO> createReaction(@Valid @RequestBody ReactionDTO reactionDTO) {
//        return ResponseEntity.ok(reactionService.createReaction(reactionDTO));
//    }
//
//
//    @GetMapping("/comment/{commentId}")
//    public ResponseEntity<List<ReactionResponseDTO>> getReactionsByComment(@PathVariable Long commentId) {
//        return ResponseEntity.ok(reactionService.getReactionsByComment(commentId));
//    }
//}
