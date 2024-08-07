package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.model.entity.PetBreed;

import java.util.List;

public interface PetBreedService {
    PetBreed createPetBreed(PetBreed petBreed);
    PetBreed getPetBreedById(Long id);
    List<PetBreed> getAllPetBreeds();
    List<PetBreed> getBreedsByType(Long typeId);
    PetBreed updatePetBreed(Long id, PetBreed petBreed);
    void deletePetBreed(Long id);
}
