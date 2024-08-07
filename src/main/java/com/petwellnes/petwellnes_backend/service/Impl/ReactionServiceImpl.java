package com.petwellnes.petwellnes_backend.service.Impl;

import com.petwellnes.petwellnes_backend.repository.PostRepository;
import com.petwellnes.petwellnes_backend.repository.ReactionRepository;
import com.petwellnes.petwellnes_backend.repository.UserRepository;
import com.petwellnes.petwellnes_backend.mapper.ReactionMapper;
import com.petwellnes.petwellnes_backend.model.dto.reactionDTO.ReactionCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.reactionDTO.ReactionDTO;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import com.petwellnes.petwellnes_backend.model.entity.Reaction;
import com.petwellnes.petwellnes_backend.model.entity.User;
import com.petwellnes.petwellnes_backend.service.ReactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReactionMapper reactionMapper;

    @Override
    @Transactional
    public ReactionDTO addOrUpdateReaction(ReactionCreateDTO reactionCreateDTO) {
        Post post = postRepository.findById(reactionCreateDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Publicación no encontrada"));

        User user = userRepository.findById(reactionCreateDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Optional<Reaction> existingReaction = reactionRepository.findByPostAndUser(post, user);

        Reaction reaction;
        if (existingReaction.isPresent()) {
            reaction = existingReaction.get();
            reaction.setType("heart");
        } else {
            reaction = new Reaction();
            reaction.setPost(post);
            reaction.setUser(user);
            reaction.setType("heart");
        }

        reactionRepository.save(reaction);

        return reactionMapper.convertToDTO(reaction);
    }

    @Override
    public List<ReactionDTO> getReactionsByPostId(Long postId) {
        List<Reaction> reactions = reactionRepository.findByPost_PostId(postId);
        return reactionMapper.convertListToDTO(reactions);
    }
}
