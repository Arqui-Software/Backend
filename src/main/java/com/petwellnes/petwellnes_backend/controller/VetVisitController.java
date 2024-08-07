package com.petwellnes.petwellnes_backend.controller;

import com.petwellnes.petwellnes_backend.model.dto.vetvisitDto.VetVisitCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.vetvisitDto.VetVisitUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.VetVisit;
import com.petwellnes.petwellnes_backend.service.VetVisitService;
import com.petwellnes.petwellnes_backend.infra.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vetvisits")
public class VetVisitController {

    @Autowired
    private VetVisitService vetVisitService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<VetVisit> createVetVisit(@Valid @RequestBody VetVisitCreateDTO vetVisitCreateDTO, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        VetVisit newVetVisit = vetVisitService.createVetVisit(vetVisitCreateDTO, userId);
        return ResponseEntity.ok(newVetVisit);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<VetVisit>> getAllVetVisitsByPetId(@PathVariable Long petId, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        List<VetVisit> vetVisits = vetVisitService.getAllVetVisitsByPetId(petId, userId);
        return ResponseEntity.ok(vetVisits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VetVisit> getVetVisitById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        VetVisit vetVisit = vetVisitService.getVetVisitById(id, userId)
                .orElseThrow(() -> new RuntimeException("VetVisit not found"));
        return ResponseEntity.ok().body(vetVisit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VetVisit> updateVetVisit(@PathVariable Long id, @Valid @RequestBody VetVisitUpdateDTO vetVisitUpdateDTO, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        VetVisit updatedVetVisit = vetVisitService.updateVetVisit(id, vetVisitUpdateDTO, userId);
        return ResponseEntity.ok(updatedVetVisit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVetVisit(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtService.getUserIdFromToken(jwtToken);
        vetVisitService.deleteVetVisit(id, userId);
        return ResponseEntity.noContent().build();
    }
}
