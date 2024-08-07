package com.petwellnes.petwellnes_backend.repository;

import com.petwellnes.petwellnes_backend.model.entity.PetBreed;
import com.petwellnes.petwellnes_backend.model.entity.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetBreedRepository extends JpaRepository<PetBreed, Long> {
    List<PetBreed> findByPetType_PetTypeId(Long typeId);

    boolean existsByNameAndPetType(String name, PetType petType);
}
