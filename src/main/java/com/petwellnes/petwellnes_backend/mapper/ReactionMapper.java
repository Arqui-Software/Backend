package com.petwellnes.petwellnes_backend.mapper;

import com.petwellnes.petwellnes_backend.model.dto.reactionDTO.ReactionDTO;
import com.petwellnes.petwellnes_backend.model.entity.Reaction;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReactionMapper {
    private final ModelMapper modelMapper;

    public ReactionDTO convertToDTO(Reaction reaction) {
        return modelMapper.map(reaction, ReactionDTO.class);
    }

    public List<ReactionDTO> convertListToDTO(List<Reaction> reactions) {
        return reactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
