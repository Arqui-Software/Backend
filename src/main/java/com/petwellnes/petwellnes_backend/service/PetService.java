package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.model.dto.petDto.PetCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.petDto.PetUpdateDTO;
import com.petwellnes.petwellnes_backend.model.dto.petTypeDto.PetTypeDTO;
import com.petwellnes.petwellnes_backend.model.entity.Pet;
import com.petwellnes.petwellnes_backend.model.entity.PetBreed;

import java.util.List;

public interface PetService {
    Pet createPet(PetCreateDTO petCreateDTO, Long userId);

    List<Pet> getPetsByUserId(Long userId);

    Pet getPetByIdAndUserId(Long id, Long userId);

    Pet updatePet(Long id, PetUpdateDTO petUpdateDTO, Long userId);

    Pet updatePet(Long id, Pet pet);

    void deletePet(Long id, Long userId);

    List<PetTypeDTO> getAllPetTypes();

    List<PetBreed> getBreedsByType(Long typeId);
}
