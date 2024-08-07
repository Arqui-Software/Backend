package com.petwellnes.petwellnes_backend.controller;

import com.petwellnes.petwellnes_backend.model.entity.PetType;
import com.petwellnes.petwellnes_backend.service.PetTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet-type")
@RequiredArgsConstructor
public class PetTypeController {

    private final PetTypeService petTypeService;

    @PostMapping
    public ResponseEntity<PetType> createPetType(@RequestBody @Valid PetType petType) {
        PetType createdPetType = petTypeService.createPetType(petType);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPetType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetType> getPetTypeById(@PathVariable Long id) {
        PetType petType = petTypeService.getPetTypeById(id);
        return ResponseEntity.ok(petType);
    }

    @GetMapping
    public ResponseEntity<List<PetType>> getAllPetTypes() {
        List<PetType> petTypes = petTypeService.getAllPetTypes();
        return ResponseEntity.ok(petTypes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetType> updatePetType(@PathVariable Long id, @RequestBody @Valid PetType petType) {
        PetType updatedPetType = petTypeService.updatePetType(id, petType);
        return ResponseEntity.ok(updatedPetType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetType(@PathVariable Long id) {
        petTypeService.deletePetType(id);
        return ResponseEntity.noContent().build();
    }
}
