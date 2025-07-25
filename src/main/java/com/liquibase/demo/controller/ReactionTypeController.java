package com.liquibase.demo.controller;

import com.liquibase.demo.dto.ReactionTypeRequestDTO;
import com.liquibase.demo.dto.ReactionTypeResponseDTO;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.service.reactionTypeService.ReactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ReactionTypeController {

    @Autowired
    private ReactionTypeService reactionTypeService;

    @PostMapping
    public ResponseEntity<APIResponse<ReactionTypeResponseDTO>> create(@RequestBody ReactionTypeRequestDTO dto) {
        ReactionTypeResponseDTO created = reactionTypeService.createReactionType(dto);

        return new ResponseEntity<>(new APIResponse<>("Created successfully", HttpStatus.CREATED, created), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ReactionTypeResponseDTO>>> getAll() {
        List<ReactionTypeResponseDTO> list = reactionTypeService.getAllReactionTypes();
        return ResponseEntity.ok(new APIResponse<>("Fetched successfully", HttpStatus.OK, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ReactionTypeResponseDTO>> getById(@PathVariable Long id) {
        ReactionTypeResponseDTO dto = reactionTypeService.getReactionTypeById(id);
        return ResponseEntity.ok(new APIResponse<>("Fetched successfully", HttpStatus.OK, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ReactionTypeResponseDTO>> update(@PathVariable Long id, @RequestBody ReactionTypeRequestDTO dto) {
        ReactionTypeResponseDTO updated = reactionTypeService.updateReactionType(id, dto);
        return ResponseEntity.ok(new APIResponse<>("Updated successfully", HttpStatus.OK, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable Long id) {
        reactionTypeService.deleteReactionType(id);
        return ResponseEntity.ok(new APIResponse<>("Deleted (soft) successfully", HttpStatus.OK, "Deleted"));
    }
}
