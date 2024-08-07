package com.petwellnes.petwellnes_backend.service.Impl;

import com.petwellnes.petwellnes_backend.repository.PetBreedRepository;
import com.petwellnes.petwellnes_backend.repository.PetRepository;
import com.petwellnes.petwellnes_backend.repository.PetTypeRepository;
import com.petwellnes.petwellnes_backend.repository.UserRepository;
import com.petwellnes.petwellnes_backend.model.dto.petDto.PetCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.petDto.PetUpdateDTO;
import com.petwellnes.petwellnes_backend.model.dto.petTypeDto.PetTypeDTO;
import com.petwellnes.petwellnes_backend.model.entity.Pet;
import com.petwellnes.petwellnes_backend.model.entity.PetBreed;
import com.petwellnes.petwellnes_backend.model.entity.PetType;
import com.petwellnes.petwellnes_backend.model.entity.User;
import com.petwellnes.petwellnes_backend.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private PetBreedRepository petBreedRepository;

    @Override
    public Pet createPet(PetCreateDTO petCreateDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pet pet = new Pet();
        pet.setName(petCreateDTO.getName());
        pet.setSpecies(petTypeRepository.findById(Long.parseLong(petCreateDTO.getSpecies())).orElseThrow(() -> new RuntimeException("Species not found")));
        pet.setBreed(petBreedRepository.findById(Long.parseLong(petCreateDTO.getBreed())).orElseThrow(() -> new RuntimeException("Breed not found")));
        pet.setAge(petCreateDTO.getAge());
        pet.setUser(user);

        if (petCreateDTO.getProfilePhoto() != null && !petCreateDTO.getProfilePhoto().isEmpty()) {
            pet.setProfilePhoto(petCreateDTO.getProfilePhoto());
        }

        if (petCreateDTO.getPhoto() != null && !petCreateDTO.getPhoto().isEmpty()) {
            pet.setPhoto(petCreateDTO.getPhoto());
        }

        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Long id, PetUpdateDTO petUpdateDTO, Long userId) {
        Pet existingPet = petRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        existingPet.setName(petUpdateDTO.getName());
        existingPet.setSpecies(petTypeRepository.findById(Long.parseLong(petUpdateDTO.getSpecies())).orElseThrow(() -> new RuntimeException("Species not found")));
        existingPet.setBreed(petBreedRepository.findById(Long.parseLong(petUpdateDTO.getBreed())).orElseThrow(() -> new RuntimeException("Breed not found")));
        existingPet.setAge(petUpdateDTO.getAge());

        if (petUpdateDTO.getProfilePhoto() != null && !petUpdateDTO.getProfilePhoto().isEmpty()) {
            existingPet.setProfilePhoto(petUpdateDTO.getProfilePhoto());
        }

        if (petUpdateDTO.getPhoto() != null && !petUpdateDTO.getPhoto().isEmpty()) {
            existingPet.setPhoto(petUpdateDTO.getPhoto());
        }

        return petRepository.save(existingPet);
    }

    @Override
    public Pet updatePet(Long id, Pet pet) {
        Pet existingPet = petRepository.findByIdAndUserId(id, pet.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        existingPet.setName(pet.getName());
        existingPet.setSpecies(pet.getSpecies());
        existingPet.setBreed(pet.getBreed());
        existingPet.setAge(pet.getAge());
        existingPet.setProfilePhoto(pet.getProfilePhoto());
        existingPet.setPhoto(pet.getPhoto());

        return petRepository.save(existingPet);
    }

    @Override
    public List<Pet> getPetsByUserId(Long userId) {
        return petRepository.findByUserId(userId);
    }

    @Override
    public Pet getPetByIdAndUserId(Long id, Long userId) {
        return petRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    @Override
    public void deletePet(Long id, Long userId) {
        Pet existingPet = petRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        petRepository.delete(existingPet);
    }

    @Override
    public List<PetTypeDTO> getAllPetTypes() {
        List<PetType> petTypes = petTypeRepository.findAll();
        return petTypes.stream()
                .map(petType -> new PetTypeDTO(petType.getPetTypeId(), petType.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PetBreed> getBreedsByType(Long typeId) {
        return petBreedRepository.findByPetType_PetTypeId(typeId);
    }
}
