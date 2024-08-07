package com.petwellnes.petwellnes_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PetType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petTypeId;

    @Column(nullable = false)
    private String name;

    public void setPetTypeId(Long id) {
        this.petTypeId = id;
    }
}
