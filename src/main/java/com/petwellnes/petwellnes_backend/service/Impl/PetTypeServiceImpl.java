package com.petwellnes.petwellnes_backend.service.Impl;

import com.petwellnes.petwellnes_backend.repository.PetTypeRepository;
import com.petwellnes.petwellnes_backend.model.entity.PetType;
import com.petwellnes.petwellnes_backend.service.PetTypeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetTypeServiceImpl implements PetTypeService {
    private final PetTypeRepository petTypeRepository;

    @Override
    public PetType createPetType(PetType petType) {
        return petTypeRepository.save(petType);
    }

    @Override
    public PetType getPetTypeById(Long id) {
        return petTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de mascota no encontrado"));
    }

    @Override
    public List<PetType> getAllPetTypes() {
        return petTypeRepository.findAll();
    }

    @Override
    public PetType updatePetType(Long id, PetType petType) {
        PetType existingPetType = petTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de mascota no encontrado"));
        existingPetType.setName(petType.getName());
        return petTypeRepository.save(existingPetType);
    }

    @Override
    public void deletePetType(Long id) {
        petTypeRepository.deleteById(id);
    }
}
