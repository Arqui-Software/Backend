package com.petwellnes.petwellnes_backend.repository;

import com.petwellnes.petwellnes_backend.model.entity.VetVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VetVisitRepository extends JpaRepository<VetVisit, Long> {

    @Query("SELECT vv FROM VetVisit vv WHERE vv.pet.id = :petId AND vv.pet.user.userId = :userId")
    List<VetVisit> findByPetIdAndUserUserId(@Param("petId") Long petId, @Param("userId") Long userId);

    @Query("SELECT vv FROM VetVisit vv WHERE vv.id = :id AND vv.pet.user.userId = :userId")
    Optional<VetVisit> findByIdAndUserUserId(@Param("id") Long id, @Param("userId") Long userId);

}
