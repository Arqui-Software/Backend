package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.model.dto.postDto.PostCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.postDto.PostDTO;
import com.petwellnes.petwellnes_backend.model.dto.postDto.PostUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.Post;

import java.util.List;
import java.util.Map;

public interface PostService {
    PostDTO createPost(PostCreateDTO postCreateDTO);

    PostDTO getPostById(Long postId);

    List<PostDTO> getAllPosts(int page, int size);

    PostDTO updatePost(Long postId, PostUpdateDTO postUpdateDTO);

    void deletePost(Long postId);

    List<PostDTO> getRecentPosts();

    Map<String, List<PostDTO>> getRecentPostsGroupedByType();

    List<PostDTO> getResponsesByParentPostId(Long parentPostId);

    List<PostDTO> getPostsByAnimalType(Long animalTypeId);

    List<PostDTO> getPostsByUserId(Long userId);

    List<PostDTO> getPostsByBreed(Long breedId);
}