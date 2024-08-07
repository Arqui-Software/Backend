package com.petwellnes.petwellnes_backend.model.dto.vetvisitDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VetVisitUpdateDTO {
    private LocalDate date;
    private String reason;
    private String notes;
}
