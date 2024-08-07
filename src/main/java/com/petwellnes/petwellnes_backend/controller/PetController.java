package com.petwellnes.petwellnes_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petwellnes.petwellnes_backend.model.dto.petDto.PetCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.petDto.PetDto;
import com.petwellnes.petwellnes_backend.model.dto.petDto.PetUpdateDTO;
import com.petwellnes.petwellnes_backend.model.dto.petTypeDto.PetTypeDTO;
import com.petwellnes.petwellnes_backend.model.entity.Pet;
import com.petwellnes.petwellnes_backend.model.entity.PetBreed;
import com.petwellnes.petwellnes_backend.service.PetService;
import com.petwellnes.petwellnes_backend.infra.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<Pet> createPet(
            @RequestPart("pet") String petJson,
            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @RequestHeader("Authorization") String token) throws IOException {

        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);

        ObjectMapper objectMapper = new ObjectMapper();
        PetCreateDTO petCreateDTO = objectMapper.readValue(petJson, PetCreateDTO.class);

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            petCreateDTO.setProfilePhoto(convertToBase64(profilePhoto));
        }

        Pet newPet = petService.createPet(petCreateDTO, userId);
        return ResponseEntity.ok(newPet);
    }

    private String convertToBase64(MultipartFile file) throws IOException {
        return java.util.Base64.getEncoder().encodeToString(file.getBytes());
    }

    @GetMapping("/selectbyuser")
    public ResponseEntity<List<PetDto>> getPetsByUser(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        List<Pet> pets = petService.getPetsByUserId(userId);
        List<PetDto> petDtos = pets.stream().map(pet -> {
            PetDto dto = new PetDto();
            dto.setId(pet.getId());
            dto.setName(pet.getName());
            dto.setSpeciesName(pet.getSpecies().getName());
            dto.setBreedName(pet.getBreed().getName());
            dto.setAge(pet.getAge());
            dto.setProfilePhoto(pet.getProfilePhoto());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(petDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        Pet pet = petService.getPetByIdAndUserId(id, userId);
        return ResponseEntity.ok(pet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(
            @PathVariable Long id,
            @RequestPart("pet") String petJson,
            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @RequestHeader("Authorization") String token) throws IOException {

        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);

        ObjectMapper objectMapper = new ObjectMapper();
        PetUpdateDTO petUpdateDTO = objectMapper.readValue(petJson, PetUpdateDTO.class);

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            petUpdateDTO.setProfilePhoto(convertToBase64(profilePhoto));
        }

        Pet updatedPet = petService.updatePet(id, petUpdateDTO, userId);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        petService.deletePet(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pet-type")
    public ResponseEntity<List<PetTypeDTO>> getAnimalTypes() {
        List<PetTypeDTO> petTypes = petService.getAllPetTypes();
        return ResponseEntity.ok(petTypes);
    }

    @GetMapping("/pet-breed/by-type")
    public ResponseEntity<List<PetBreed>> getBreedsByType(@RequestParam Long typeId) {
        List<PetBreed> breeds = petService.getBreedsByType(typeId);
        return ResponseEntity.ok(breeds);
    }

    @PostMapping("/upload-profile-image")
    public ResponseEntity<String> uploadProfileImage(
            @RequestPart("file") MultipartFile file,
            @RequestParam("petId") Long petId,
            @RequestHeader("Authorization") String token) throws IOException {

        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);

        Pet pet = petService.getPetByIdAndUserId(petId, userId);
        if (pet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
        }

        String profilePhoto = java.util.Base64.getEncoder().encodeToString(file.getBytes());
        pet.setProfilePhoto(profilePhoto);

        petService.updatePet(petId, pet);

        return ResponseEntity.ok(profilePhoto);
    }
}
