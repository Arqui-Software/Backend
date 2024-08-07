package com.petwellnes.petwellnes_backend.service;

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
import com.petwellnes.petwellnes_backend.service.Impl.PetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetTypeRepository petTypeRepository;

    @Mock
    private PetBreedRepository petBreedRepository;

    @InjectMocks
    private PetServiceImpl petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPet_Success() {
        PetCreateDTO petCreateDTO = new PetCreateDTO("Buddy", "1", "2", 5, "profilePhoto", "photo");
        User user = new User();
        user.setUserId(1L);
        PetType petType = new PetType();
        petType.setPetTypeId(1L);
        petType.setName("Dog");
        PetBreed petBreed = new PetBreed();
        petBreed.setPetBreedId(2L);
        petBreed.setName("Bulldog");
        petBreed.setPetType(petType);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(petTypeRepository.findById(1L)).thenReturn(Optional.of(petType));
        when(petBreedRepository.findById(2L)).thenReturn(Optional.of(petBreed));
        when(petRepository.save(any(Pet.class))).thenReturn(new Pet());

        Pet pet = petService.createPet(petCreateDTO, 1L);

        assertNotNull(pet);
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    void createPet_UserNotFound() {
        PetCreateDTO petCreateDTO = new PetCreateDTO("Buddy", "1", "2", 5, "profilePhoto", "photo");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> petService.createPet(petCreateDTO, 1L));
    }

    @Test
    void createPet_SpeciesNotFound() {
        PetCreateDTO petCreateDTO = new PetCreateDTO("Buddy", "1", "2", 5, "profilePhoto", "photo");
        User user = new User();
        user.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(petTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> petService.createPet(petCreateDTO, 1L));
    }

    @Test
    void createPet_BreedNotFound() {
        PetCreateDTO petCreateDTO = new PetCreateDTO("Buddy", "1", "2", 5, "profilePhoto", "photo");
        User user = new User();
        user.setUserId(1L);
        PetType petType = new PetType();
        petType.setPetTypeId(1L);
        petType.setName("Dog");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(petTypeRepository.findById(1L)).thenReturn(Optional.of(petType));
        when(petBreedRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> petService.createPet(petCreateDTO, 1L));
    }

    @Test
    void updatePet_Success() {
        PetUpdateDTO petUpdateDTO = new PetUpdateDTO("Buddy", "1", "2", 5, "profilePhoto", "photo");
        Pet pet = new Pet();
        pet.setUser(new User());
        PetType petType = new PetType();
        petType.setPetTypeId(1L);
        petType.setName("Dog");
        PetBreed petBreed = new PetBreed();
        petBreed.setPetBreedId(2L);
        petBreed.setName("Bulldog");
        petBreed.setPetType(petType);

        when(petRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(pet));
        when(petTypeRepository.findById(1L)).thenReturn(Optional.of(petType));
        when(petBreedRepository.findById(2L)).thenReturn(Optional.of(petBreed));
        when(petRepository.save(any(Pet.class))).thenReturn(new Pet());

        Pet updatedPet = petService.updatePet(1L, petUpdateDTO, 1L);

        assertNotNull(updatedPet);
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    void updatePet_PetNotFound() {
        PetUpdateDTO petUpdateDTO = new PetUpdateDTO("Buddy", "1", "2", 5, "profilePhoto", "photo");

        when(petRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> petService.updatePet(1L, petUpdateDTO, 1L));
    }

    @Test
    void updatePet_SpeciesNotFound() {
        PetUpdateDTO petUpdateDTO = new PetUpdateDTO("Buddy", "1", "2", 5, "profilePhoto", "photo");
        Pet pet = new Pet();
        pet.setUser(new User());

        when(petRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(pet));
        when(petTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> petService.updatePet(1L, petUpdateDTO, 1L));
    }

    @Test
    void updatePet_BreedNotFound() {
        PetUpdateDTO petUpdateDTO = new PetUpdateDTO("Buddy", "1", "2", 5, "profilePhoto", "photo");
        Pet pet = new Pet();
        pet.setUser(new User());
        PetType petType = new PetType();
        petType.setPetTypeId(1L);
        petType.setName("Dog");

        when(petRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(pet));
        when(petTypeRepository.findById(1L)).thenReturn(Optional.of(petType));
        when(petBreedRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> petService.updatePet(1L, petUpdateDTO, 1L));
    }

    @Test
    void getPetsByUserId_Success() {
        List<Pet> pets = List.of(new Pet(), new Pet());

        when(petRepository.findByUserId(1L)).thenReturn(pets);

        List<Pet> result = petService.getPetsByUserId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void getPetByIdAndUserId_Success() {
        Pet pet = new Pet();
        pet.setUser(new User());

        when(petRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(pet));

        Pet result = petService.getPetByIdAndUserId(1L, 1L);

        assertNotNull(result);
    }

    @Test
    void getPetByIdAndUserId_PetNotFound() {
        when(petRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> petService.getPetByIdAndUserId(1L, 1L));
    }

    @Test
    void deletePet_Success() {
        Pet pet = new Pet();
        pet.setUser(new User());

        when(petRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(pet));

        petService.deletePet(1L, 1L);

        verify(petRepository, times(1)).delete(pet);
    }

    @Test
    void deletePet_PetNotFound() {
        when(petRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> petService.deletePet(1L, 1L));
    }

    @Test
    void getAllPetTypes_Success() {
        List<PetType> petTypes = List.of(new PetType(), new PetType());
        petTypes.get(0).setPetTypeId(1L);
        petTypes.get(0).setName("Dog");
        petTypes.get(1).setPetTypeId(2L);
        petTypes.get(1).setName("Cat");

        when(petTypeRepository.findAll()).thenReturn(petTypes);

        List<PetTypeDTO> result = petService.getAllPetTypes();

        assertEquals(2, result.size());
        assertEquals("Dog", result.get(0).getName());
        assertEquals("Cat", result.get(1).getName());
    }

    @Test
    void getBreedsByType_Success() {
        List<PetBreed> petBreeds = List.of(new PetBreed(), new PetBreed());
        petBreeds.get(0).setPetBreedId(1L);
        petBreeds.get(0).setName("Bulldog");
        petBreeds.get(1).setPetBreedId(2L);
        petBreeds.get(1).setName("Poodle");

        when(petBreedRepository.findByPetType_PetTypeId(1L)).thenReturn(petBreeds);

        List<PetBreed> result = petService.getBreedsByType(1L);

        assertEquals(2, result.size());
        assertEquals("Bulldog", result.get(0).getName());
        assertEquals("Poodle", result.get(1).getName());
    }
}
