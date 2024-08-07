package com.petwellnes.petwellnes_backend.repository;

import com.petwellnes.petwellnes_backend.model.entity.PetType;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findByParentPost(Post parentPost);

    List<Post> findByPetType_PetTypeId(Long petTypeId);

    List<Post> findByPetType(PetType petType);

    List<Post> findByUser_UserId(Long userId);

    List<Post> findByPetBreed_PetBreedId(Long petBreedId);
}
