package com.petwellnes.petwellnes_backend.repository;

import com.petwellnes.petwellnes_backend.model.entity.Reaction;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import com.petwellnes.petwellnes_backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    List<Reaction> findByPost_PostId(Long postId);
    Optional<Reaction> findByPostAndUser(Post post, User user);
}
