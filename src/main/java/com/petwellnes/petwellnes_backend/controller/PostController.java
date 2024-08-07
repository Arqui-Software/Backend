package com.petwellnes.petwellnes_backend.controller;

import com.petwellnes.petwellnes_backend.model.dto.postDto.PostCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.postDto.PostDTO;
import com.petwellnes.petwellnes_backend.model.dto.postDto.PostUpdateDTO;
import com.petwellnes.petwellnes_backend.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid PostCreateDTO postCreateDTO) {
        try {
            PostDTO postDTO = postService.createPost(postCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(postDTO);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {
        try {
            PostDTO postDTO = postService.getPostById(postId);
            return ResponseEntity.ok(postDTO);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<PostDTO> posts = postService.getAllPosts(page, size);
            return ResponseEntity.ok(posts);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(
            @PathVariable Long postId,
            @RequestBody @Valid PostUpdateDTO postUpdateDTO) {
        try {
            PostDTO postDTO = postService.updatePost(postId, postUpdateDTO);
            return ResponseEntity.ok(postDTO);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PostDTO>> getRecentPosts() {
        List<PostDTO> recentPosts = postService.getRecentPosts();
        return ResponseEntity.ok(recentPosts);
    }

    @GetMapping("/recent-grouped")
    public ResponseEntity<Map<String, List<PostDTO>>> getRecentPostsGroupedByType() {
        Map<String, List<PostDTO>> recentPostsGroupedByType = postService.getRecentPostsGroupedByType();
        return ResponseEntity.ok(recentPostsGroupedByType);
    }

    @GetMapping("/{id}/responses")
    public ResponseEntity<List<PostDTO>> getResponsesByParentPostId(@PathVariable Long id) {
        List<PostDTO> responses = postService.getResponsesByParentPostId(id);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-animal-type")
    public ResponseEntity<List<PostDTO>> getPostsByAnimalType(@RequestParam Long animalTypeId) {
        try {
            List<PostDTO> posts = postService.getPostsByAnimalType(animalTypeId);
            return ResponseEntity.ok(posts);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@RequestParam Long userId) {
        try {
            List<PostDTO> posts = postService.getPostsByUserId(userId);
            return ResponseEntity.ok(posts);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-breed")
    public ResponseEntity<List<PostDTO>> getPostsByBreed(@RequestParam Long breedId) {
        try {
            List<PostDTO> posts = postService.getPostsByBreed(breedId);
            return ResponseEntity.ok(posts);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
