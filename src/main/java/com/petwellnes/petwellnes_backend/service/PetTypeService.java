package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.model.entity.PetType;

import java.util.List;

public interface PetTypeService {
    PetType createPetType(PetType petType);
    PetType getPetTypeById(Long id);
    List<PetType> getAllPetTypes();
    PetType updatePetType(Long id, PetType petType);
    void deletePetType(Long id);
}