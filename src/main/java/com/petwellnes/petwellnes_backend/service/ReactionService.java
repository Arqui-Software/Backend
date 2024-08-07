package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.model.dto.reactionDTO.ReactionCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.reactionDTO.ReactionDTO;

import java.util.List;

public interface ReactionService {
    ReactionDTO addOrUpdateReaction(ReactionCreateDTO reactionCreateDTO);
    List<ReactionDTO> getReactionsByPostId(Long postId);
}
