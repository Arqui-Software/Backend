package com.petwellnes.petwellnes_backend.controller;

import com.petwellnes.petwellnes_backend.infra.security.JwtService;
import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentCreatedResponseDTO;
import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentDTO;
import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.User;
import com.petwellnes.petwellnes_backend.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtService jwtService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId,
                                                    @RequestBody CommentCreateDTO commentCreateDTO,
                                                    @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        CommentDTO newComment = commentService.createComment(commentCreateDTO, userId, postId);
        return ResponseEntity.ok(newComment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId,
                                                    @RequestBody CommentUpdateDTO commentUpdateDTO,
                                                    @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        CommentDTO updatedComment = commentService.updateComment(commentId, commentUpdateDTO, userId);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        try {
            commentService.deleteComment(commentId, userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{postId}/reply/{parentCommentId}")
    public ResponseEntity<CommentCreatedResponseDTO> replyToComment(@PathVariable Long postId,
                                                                    @PathVariable Long parentCommentId,
                                                                    @RequestBody CommentCreateDTO createCommentDTO,
                                                                    @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        User user = new User();
        user.setUserId(userId);
        CommentCreatedResponseDTO response = commentService.replyToComment(postId, parentCommentId, createCommentDTO, user);
        return ResponseEntity.ok(response);
    }
}
