package com.petwellnes.petwellnes_backend.mapper;

import com.petwellnes.petwellnes_backend.model.dto.petDto.PetCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.petDto.PetUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.Pet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Pet toEntity(PetCreateDTO petCreateDTO) {
        return modelMapper.map(petCreateDTO, Pet.class);
    }

    public Pet toEntity(PetUpdateDTO petUpdateDTO) {
        return modelMapper.map(petUpdateDTO, Pet.class);
    }

    public PetCreateDTO toCreateDto(Pet pet) {
        return modelMapper.map(pet, PetCreateDTO.class);
    }

    public PetUpdateDTO toUpdateDto(Pet pet) {
        return modelMapper.map(pet, PetUpdateDTO.class);
    }
}
