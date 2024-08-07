package com.petwellnes.petwellnes_backend.model.dto.CommentDto;

import lombok.Data;

@Data
public class CommentCreatedResponseDTO {
    private String content;
    private Long commentId;
    private Long userId;
    private Long postId;
    private String username;
    private Long parentCommentId;

    public CommentCreatedResponseDTO(String content, Long commentId, Long userId, Long postId, String username, Long parentCommentId) {
        this.content = content;
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.username = username;
        this.parentCommentId = parentCommentId;
    }
}