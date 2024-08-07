package com.petwellnes.petwellnes_backend.service.Impl;

import com.petwellnes.petwellnes_backend.model.dto.vetvisitDto.VetVisitCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.vetvisitDto.VetVisitUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.Pet;
import com.petwellnes.petwellnes_backend.model.entity.VetVisit;
import com.petwellnes.petwellnes_backend.infra.exception.CustomException;
import com.petwellnes.petwellnes_backend.repository.PetRepository;
import com.petwellnes.petwellnes_backend.repository.VetVisitRepository;
import com.petwellnes.petwellnes_backend.service.VetVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VetVisitServiceImpl implements VetVisitService {

    @Autowired
    private VetVisitRepository vetVisitRepository;

    @Autowired
    private PetRepository petRepository;

    @Override
    public List<VetVisit> getAllVetVisitsByPetId(Long petId, Long userId) {
        return vetVisitRepository.findByPetIdAndUserUserId(petId, userId);
    }

    @Override
    public Optional<VetVisit> getVetVisitById(Long id, Long userId) {
        return vetVisitRepository.findByIdAndUserUserId(id, userId);
    }

    @Override
    public VetVisit createVetVisit(VetVisitCreateDTO vetVisitCreateDTO, Long userId) {
        Pet pet = petRepository.findByIdAndUserId(vetVisitCreateDTO.getPetId(), userId)
                .orElseThrow(() -> new CustomException("Pet not found"));

        VetVisit vetVisit = new VetVisit();
        vetVisit.setDate(vetVisitCreateDTO.getDate());
        vetVisit.setReason(vetVisitCreateDTO.getReason());
        vetVisit.setNotes(vetVisitCreateDTO.getNotes());
        vetVisit.setPet(pet);
        vetVisit.setUser(pet.getUser()); // Asignar el usuario del propietario de la mascota

        return vetVisitRepository.save(vetVisit);
    }

    @Override
    public VetVisit updateVetVisit(Long id, VetVisitUpdateDTO vetVisitUpdateDTO, Long userId) {
        VetVisit vetVisit = vetVisitRepository.findByIdAndUserUserId(id, userId)
                .orElseThrow(() -> new CustomException("VetVisit not found"));

        vetVisit.setDate(vetVisitUpdateDTO.getDate());
        vetVisit.setReason(vetVisitUpdateDTO.getReason());
        vetVisit.setNotes(vetVisitUpdateDTO.getNotes());

        return vetVisitRepository.save(vetVisit);
    }

    @Override
    public void deleteVetVisit(Long id, Long userId) {
        VetVisit vetVisit = vetVisitRepository.findByIdAndUserUserId(id, userId)
                .orElseThrow(() -> new CustomException("VetVisit not found"));
        vetVisitRepository.delete(vetVisit);
    }
}
