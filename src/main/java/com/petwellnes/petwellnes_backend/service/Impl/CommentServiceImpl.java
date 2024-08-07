package com.petwellnes.petwellnes_backend.service.Impl;

import com.petwellnes.petwellnes_backend.infra.exception.ResourceNotFoundException;
import com.petwellnes.petwellnes_backend.repository.CommentRepository;
import com.petwellnes.petwellnes_backend.repository.PostRepository;
import com.petwellnes.petwellnes_backend.repository.UserRepository;
import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentCreatedResponseDTO;
import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentDTO;
import com.petwellnes.petwellnes_backend.model.dto.CommentDto.CommentUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.Comment;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import com.petwellnes.petwellnes_backend.model.entity.User;
import com.petwellnes.petwellnes_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public CommentDTO createComment(CommentCreateDTO commentCreateDTO, Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setUser(user);
        comment.setPost(post);
        comment.setParentComment(commentCreateDTO.getParentCommentId() != null ?
                commentRepository.findById(commentCreateDTO.getParentCommentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found")) : null);

        comment = commentRepository.save(comment);

        return mapToDTO(comment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPost_PostId(postId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO updateComment(Long commentId, CommentUpdateDTO commentUpdateDTO, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new ResourceNotFoundException("You are not allowed to edit this comment");
        }

        comment.setContent(commentUpdateDTO.getContent());
        comment = commentRepository.save(comment);

        return mapToDTO(comment);
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("Comment not found"));

        if (!comment.getUserId().equals(userId)) {
            throw new IllegalStateException("User not authorized to delete this comment");
        }

        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            throw new IllegalStateException("Cannot delete a comment that has replies");
        }

        commentRepository.delete(comment);
    }

    @Override
    public CommentCreatedResponseDTO replyToComment(Long postId, Long parentCommentId, CommentCreateDTO createCommentDTO, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));

        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + parentCommentId));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setParentComment(parentComment);
        comment.setContent(createCommentDTO.getContent());
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return new CommentCreatedResponseDTO(
                savedComment.getContent(),
                savedComment.getId(),
                savedComment.getUser().getUserId(),
                savedComment.getPost().getPostId(),
                savedComment.getUser().getUsername(),
                savedComment.getParentComment() != null ? savedComment.getParentComment().getId() : null
        );
    }


    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setUserId(comment.getUser().getUserId());
        commentDTO.setPostId(comment.getPost().getPostId());
        commentDTO.setUsername(comment.getUser().getUsername());
        commentDTO.setParentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null);
        return commentDTO;
    }
}
