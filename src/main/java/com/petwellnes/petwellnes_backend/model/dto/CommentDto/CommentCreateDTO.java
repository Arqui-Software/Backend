package com.petwellnes.petwellnes_backend.model.dto.CommentDto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private String content;
    private Long parentCommentId;
}
