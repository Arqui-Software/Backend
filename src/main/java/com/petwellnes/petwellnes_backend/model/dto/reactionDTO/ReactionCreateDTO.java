package com.petwellnes.petwellnes_backend.model.dto.reactionDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReactionCreateDTO {
    @NotNull
    private Long postId;

    @NotNull
    private Long userId;

    @NotBlank
    private String type;
}
