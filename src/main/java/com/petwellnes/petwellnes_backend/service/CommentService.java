package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentCreatedResponseDTO;
import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentDTO;
import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.User;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(CommentCreateDTO commentCreateDTO, Long userId, Long postId);

    List<CommentDTO> getCommentsByPostId(Long postId);

    CommentDTO updateComment(Long commentId, CommentUpdateDTO commentUpdateDTO, Long userId);

    void deleteComment(Long commentId, Long userId);

    CommentCreatedResponseDTO replyToComment(Long postId, Long parentCommentId, CommentCreateDTO createCommentDTO, User user);
}
