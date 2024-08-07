package com.petwellnes.petwellnes_backend.model.dto.reactionDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReactionDTO {
    private Long id;
    private Long postId;
    private Long userId;
    private String type;
}
