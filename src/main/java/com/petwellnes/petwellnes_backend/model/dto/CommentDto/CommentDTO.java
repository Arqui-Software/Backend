package com.petwellnes.petwellnes_backend.model.dto.CommentDto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private Long userId;
    private Long postId;
    private String username;
    private Long parentCommentId;
}