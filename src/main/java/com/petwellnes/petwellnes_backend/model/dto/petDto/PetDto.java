package com.petwellnes.petwellnes_backend.model.dto.petDto;

import lombok.Data;

@Data
public class PetDto {
    private Long id;
    private String name;
    private String speciesName;
    private String breedName;
    private Integer age;
    private String profilePhoto;
    private String photo;
}
