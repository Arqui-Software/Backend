package com.petwellnes.petwellnes_backend.service.Impl;

import com.petwellnes.petwellnes_backend.repository.PetBreedRepository;
import com.petwellnes.petwellnes_backend.model.entity.PetBreed;
import com.petwellnes.petwellnes_backend.service.PetBreedService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetBreedServiceImpl implements PetBreedService {
    private final PetBreedRepository petBreedRepository;

    @Override
    public PetBreed createPetBreed(PetBreed petBreed) {
        return petBreedRepository.save(petBreed);
    }

    @Override
    public PetBreed getPetBreedById(Long id) {
        return petBreedRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raza de mascota no encontrada"));
    }

    @Override
    public List<PetBreed> getAllPetBreeds() {
        return petBreedRepository.findAll();
    }

    @Override
    public List<PetBreed> getBreedsByType(Long typeId) {
        return petBreedRepository.findByPetType_PetTypeId(typeId);
    }

    @Override
    public PetBreed updatePetBreed(Long id, PetBreed petBreed) {
        PetBreed existingPetBreed = petBreedRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raza de mascota no encontrada"));
        existingPetBreed.setName(petBreed.getName());
        existingPetBreed.setPetType(petBreed.getPetType());
        return petBreedRepository.save(existingPetBreed);
    }

    @Override
    public void deletePetBreed(Long id) {
        petBreedRepository.deleteById(id);
    }
}
