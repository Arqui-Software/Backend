package com.petwellnes.petwellnes_backend.model.dto.petDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetUpdateDTO {
    private String name;
    private String species;
    private String breed;
    private Integer age;
    private String profilePhoto;
    private String photo;
}