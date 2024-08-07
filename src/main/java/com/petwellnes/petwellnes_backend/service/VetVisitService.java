package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.model.dto.vetvisitDto.VetVisitCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.vetvisitDto.VetVisitUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.VetVisit;

import java.util.List;
import java.util.Optional;

public interface VetVisitService {
    List<VetVisit> getAllVetVisitsByPetId(Long petId, Long userId);

    Optional<VetVisit> getVetVisitById(Long id, Long userId);

    VetVisit createVetVisit(VetVisitCreateDTO vetVisitCreateDTO, Long userId);

    VetVisit updateVetVisit(Long id, VetVisitUpdateDTO vetVisitUpdateDTO, Long userId);

    void deleteVetVisit(Long id, Long userId);
}
