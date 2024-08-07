package com.petwellnes.petwellnes_backend.model.dto.postDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostCreateDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long userId;

    @NotNull
    private Long petTypeId;

    @NotNull
    private Long petBreedId;

    @NotNull
    private Long topicId;
}