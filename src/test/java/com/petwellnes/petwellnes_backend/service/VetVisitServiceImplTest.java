package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.infra.exception.CustomException;
import com.petwellnes.petwellnes_backend.repository.PetRepository;
import com.petwellnes.petwellnes_backend.repository.VetVisitRepository;
import com.petwellnes.petwellnes_backend.model.dto.vetvisitDto.VetVisitCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.vetvisitDto.VetVisitUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.Pet;
import com.petwellnes.petwellnes_backend.model.entity.User;
import com.petwellnes.petwellnes_backend.model.entity.VetVisit;
import com.petwellnes.petwellnes_backend.service.Impl.VetVisitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VetVisitServiceImplTest {

    @InjectMocks
    private VetVisitServiceImpl vetVisitService;

    @Mock
    private VetVisitRepository vetVisitRepository;

    @Mock
    private PetRepository petRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createVetVisit_shouldCreateVetVisit() {
        // Arrange
        VetVisitCreateDTO createDTO = new VetVisitCreateDTO(LocalDate.now(), "Checkup", "Notes", 1L);
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setUser(new User()); // Set a user for the pet

        when(petRepository.findByIdAndUserId(createDTO.getPetId(), 1L)).thenReturn(Optional.of(pet));
        when(vetVisitRepository.save(any(VetVisit.class))).thenReturn(new VetVisit());

        // Act
        VetVisit result = vetVisitService.createVetVisit(createDTO, 1L);

        // Assert
        assertNotNull(result);
        verify(vetVisitRepository, times(1)).save(any(VetVisit.class));
    }

    @Test
    void createVetVisit_shouldThrowExceptionWhenPetNotFound() {
        // Arrange
        VetVisitCreateDTO createDTO = new VetVisitCreateDTO(LocalDate.now(), "Checkup", "Notes", 1L);

        when(petRepository.findByIdAndUserId(createDTO.getPetId(), 1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomException.class, () -> vetVisitService.createVetVisit(createDTO, 1L));
    }

    @Test
    void updateVetVisit_shouldUpdateVetVisit() {
        // Arrange
        VetVisitUpdateDTO updateDTO = new VetVisitUpdateDTO(LocalDate.now(), "Checkup", "Updated Notes");
        VetVisit vetVisit = new VetVisit();

        when(vetVisitRepository.findByIdAndUserUserId(1L, 1L)).thenReturn(Optional.of(vetVisit));
        when(vetVisitRepository.save(any(VetVisit.class))).thenReturn(vetVisit);

        // Act
        VetVisit result = vetVisitService.updateVetVisit(1L, updateDTO, 1L);

        // Assert
        assertNotNull(result);
        verify(vetVisitRepository, times(1)).save(any(VetVisit.class));
    }

    @Test
    void updateVetVisit_shouldThrowExceptionWhenVetVisitNotFound() {
        // Arrange
        VetVisitUpdateDTO updateDTO = new VetVisitUpdateDTO(LocalDate.now(), "Checkup", "Updated Notes");

        when(vetVisitRepository.findByIdAndUserUserId(1L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomException.class, () -> vetVisitService.updateVetVisit(1L, updateDTO, 1L));
    }

    @Test
    void deleteVetVisit_shouldDeleteVetVisit() {
        // Arrange
        VetVisit vetVisit = new VetVisit();

        when(vetVisitRepository.findByIdAndUserUserId(1L, 1L)).thenReturn(Optional.of(vetVisit));

        // Act
        vetVisitService.deleteVetVisit(1L, 1L);

        // Assert
        verify(vetVisitRepository, times(1)).delete(vetVisit);
    }

    @Test
    void deleteVetVisit_shouldThrowExceptionWhenVetVisitNotFound() {
        // Arrange

        when(vetVisitRepository.findByIdAndUserUserId(1L, 1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomException.class, () -> vetVisitService.deleteVetVisit(1L, 1L));
    }
}
