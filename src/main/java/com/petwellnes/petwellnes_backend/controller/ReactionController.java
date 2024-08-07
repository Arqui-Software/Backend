package com.petwellnes.petwellnes_backend.controller;

import com.petwellnes.petwellnes_backend.infra.security.JwtService;
import com.petwellnes.petwellnes_backend.model.dto.reactionDTO.ReactionCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.reactionDTO.ReactionDTO;
import com.petwellnes.petwellnes_backend.service.ReactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reaction")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<ReactionDTO> addOrUpdateReaction(@RequestBody @Valid ReactionCreateDTO reactionCreateDTO, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        reactionCreateDTO.setUserId(userId);
        try {
            ReactionDTO reactionDTO = reactionService.addOrUpdateReaction(reactionCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(reactionDTO);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ReactionDTO>> getReactionsByPostId(@PathVariable Long postId) {
        try {
            List<ReactionDTO> reactions = reactionService.getReactionsByPostId(postId);
            return ResponseEntity.ok(reactions);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
