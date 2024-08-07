package com.petwellnes.petwellnes_backend.service;

import com.petwellnes.petwellnes_backend.repository.PetBreedRepository;
import com.petwellnes.petwellnes_backend.repository.PetTypeRepository;
import com.petwellnes.petwellnes_backend.repository.PostRepository;
import com.petwellnes.petwellnes_backend.repository.TopicRepository;
import com.petwellnes.petwellnes_backend.repository.UserRepository;
import com.petwellnes.petwellnes_backend.mapper.PostMapper;
import com.petwellnes.petwellnes_backend.model.dto.postDto.PostCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.postDto.PostDTO;
import com.petwellnes.petwellnes_backend.model.dto.postDto.PostUpdateDTO;
import com.petwellnes.petwellnes_backend.model.entity.PetBreed;
import com.petwellnes.petwellnes_backend.model.entity.PetType;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import com.petwellnes.petwellnes_backend.model.entity.Topic;
import com.petwellnes.petwellnes_backend.model.entity.User;
import com.petwellnes.petwellnes_backend.service.Impl.PostServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetTypeRepository petTypeRepository;

    @Mock
    private PetBreedRepository petBreedRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPost_Success() {
        PostCreateDTO postCreateDTO = new PostCreateDTO();
        postCreateDTO.setTitle("Test Title");
        postCreateDTO.setContent("Test Content");
        postCreateDTO.setUserId(1L);
        postCreateDTO.setPetTypeId(1L);
        postCreateDTO.setPetBreedId(1L);
        postCreateDTO.setTopicId(1L);

        User user = new User();
        PetType petType = new PetType();
        petType.setPetTypeId(1L);
        petType.setName("Dog");
        PetBreed petBreed = new PetBreed();
        petBreed.setPetBreedId(1L);
        petBreed.setName("Bulldog");
        petBreed.setPetType(petType);
        Topic topic = new Topic();
        topic.setTopicId(1L);
        topic.setName("Health");

        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setUser(user);
        post.setPetType(petType);
        post.setPetBreed(petBreed);
        post.setTopic(topic);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(petTypeRepository.findById(1L)).thenReturn(Optional.of(petType));
        when(petBreedRepository.findById(1L)).thenReturn(Optional.of(petBreed));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(postMapper.convertToEntity(any(PostCreateDTO.class))).thenReturn(post);
        when(postMapper.convertToDTO(any(Post.class))).thenReturn(new PostDTO());
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDTO postDTO = postService.createPost(postCreateDTO);

        assertNotNull(postDTO);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void createPost_UserNotFound() {
        PostCreateDTO postCreateDTO = new PostCreateDTO();
        postCreateDTO.setTitle("Test Title");
        postCreateDTO.setContent("Test Content");
        postCreateDTO.setUserId(1L);
        postCreateDTO.setPetTypeId(1L);
        postCreateDTO.setPetBreedId(1L);
        postCreateDTO.setTopicId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> postService.createPost(postCreateDTO));
    }

    @Test
    void createPost_PetTypeNotFound() {
        PostCreateDTO postCreateDTO = new PostCreateDTO();
        postCreateDTO.setTitle("Test Title");
        postCreateDTO.setContent("Test Content");
        postCreateDTO.setUserId(1L);
        postCreateDTO.setPetTypeId(1L);
        postCreateDTO.setPetBreedId(1L);
        postCreateDTO.setTopicId(1L);
        User user = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(petTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> postService.createPost(postCreateDTO));
    }

    @Test
    void createPost_PetBreedNotFound() {
        PostCreateDTO postCreateDTO = new PostCreateDTO();
        postCreateDTO.setTitle("Test Title");
        postCreateDTO.setContent("Test Content");
        postCreateDTO.setUserId(1L);
        postCreateDTO.setPetTypeId(1L);
        postCreateDTO.setPetBreedId(1L);
        postCreateDTO.setTopicId(1L);
        User user = new User();
        PetType petType = new PetType();
        petType.setPetTypeId(1L);
        petType.setName("Dog");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(petTypeRepository.findById(1L)).thenReturn(Optional.of(petType));
        when(petBreedRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> postService.createPost(postCreateDTO));
    }

    @Test
    void createPost_TopicNotFound() {
        PostCreateDTO postCreateDTO = new PostCreateDTO();
        postCreateDTO.setTitle("Test Title");
        postCreateDTO.setContent("Test Content");
        postCreateDTO.setUserId(1L);
        postCreateDTO.setPetTypeId(1L);
        postCreateDTO.setPetBreedId(1L);
        postCreateDTO.setTopicId(1L);
        User user = new User();
        PetType petType = new PetType();
        petType.setPetTypeId(1L);
        petType.setName("Dog");
        PetBreed petBreed = new PetBreed();
        petBreed.setPetBreedId(1L);
        petBreed.setName("Bulldog");
        petBreed.setPetType(petType);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(petTypeRepository.findById(1L)).thenReturn(Optional.of(petType));
        when(petBreedRepository.findById(1L)).thenReturn(Optional.of(petBreed));
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> postService.createPost(postCreateDTO));
    }

    @Test
    void getPostById_Success() {
        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postMapper.convertToDTO(any(Post.class))).thenReturn(new PostDTO());

        PostDTO postDTO = postService.getPostById(1L);

        assertNotNull(postDTO);
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void getPostById_PostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> postService.getPostById(1L));
    }

    @Test
    void getAllPosts_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> posts = List.of(new Post(), new Post());
        Page<Post> page = new PageImpl<>(posts);

        when(postRepository.findAll(pageable)).thenReturn(page);
        when(postMapper.convertToDTO(any(Post.class))).thenReturn(new PostDTO());

        List<PostDTO> postDTOS = postService.getAllPosts(0, 10);

        assertEquals(2, postDTOS.size());
        verify(postRepository, times(1)).findAll(pageable);
    }

    @Test
    void updatePost_Success() {
        PostUpdateDTO postUpdateDTO = new PostUpdateDTO();
        postUpdateDTO.setTitle("Updated Title");
        postUpdateDTO.setContent("Updated Content");
        postUpdateDTO.setPetTypeId(1L);
        postUpdateDTO.setPetBreedId(1L);
        postUpdateDTO.setTopicId(1L);

        Post post = new Post();
        post.setTitle("Old Title");
        post.setContent("Old Content");
        PetType petType = new PetType();
        petType.setPetTypeId(1L);
        petType.setName("Dog");
        PetBreed petBreed = new PetBreed();
        petBreed.setPetBreedId(1L);
        petBreed.setName("Bulldog");
        petBreed.setPetType(petType);
        Topic topic = new Topic();
        topic.setTopicId(1L);
        topic.setName("Health");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(petTypeRepository.findById(1L)).thenReturn(Optional.of(petType));
        when(petBreedRepository.findById(1L)).thenReturn(Optional.of(petBreed));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(postMapper.convertToDTO(any(Post.class))).thenReturn(new PostDTO());

        PostDTO postDTO = postService.updatePost(1L, postUpdateDTO);

        assertNotNull(postDTO);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void updatePost_PostNotFound() {
        PostUpdateDTO postUpdateDTO = new PostUpdateDTO();
        postUpdateDTO.setTitle("Updated Title");
        postUpdateDTO.setContent("Updated Content");
        postUpdateDTO.setPetTypeId(1L);
        postUpdateDTO.setPetBreedId(1L);
        postUpdateDTO.setTopicId(1L);

        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> postService.updatePost(1L, postUpdateDTO));
    }

    @Test
    void deletePost_Success() {
        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        postService.deletePost(1L);

        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void deletePost_PostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> postService.deletePost(1L));
    }

    @Test
    void getRecentPosts_Success() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Post> posts = List.of(new Post(), new Post());
        Page<Post> page = new PageImpl<>(posts);

        when(postRepository.findAllByOrderByCreatedAtDesc(pageable)).thenReturn(page);
        when(postMapper.convertToDTO(any(Post.class))).thenReturn(new PostDTO());

        List<PostDTO> postDTOS = postService.getRecentPosts();

        assertEquals(2, postDTOS.size());
        verify(postRepository, times(1)).findAllByOrderByCreatedAtDesc(pageable);
    }
    

    @Test
    void getResponsesByParentPostId_Success() {
        Post parentPost = new Post();
        parentPost.setTitle("Parent Post");
        parentPost.setContent("Parent Content");

        List<Post> responses = List.of(new Post(), new Post());

        when(postRepository.findById(1L)).thenReturn(Optional.of(parentPost));
        when(postRepository.findByParentPost(any(Post.class))).thenReturn(responses);
        when(postMapper.convertToDTO(any(Post.class))).thenReturn(new PostDTO());

        List<PostDTO> responseDTOS = postService.getResponsesByParentPostId(1L);

        assertEquals(2, responseDTOS.size());
        verify(postRepository, times(1)).findByParentPost(any(Post.class));
    }

    @Test
    void getPostsByAnimalType_Success() {
        List<Post> posts = List.of(new Post(), new Post());

        when(postRepository.findByPetType_PetTypeId(1L)).thenReturn(posts);
        when(postMapper.convertToDTO(any(Post.class))).thenReturn(new PostDTO());

        List<PostDTO> postDTOS = postService.getPostsByAnimalType(1L);

        assertEquals(2, postDTOS.size());
        verify(postRepository, times(1)).findByPetType_PetTypeId(1L);
    }

    @Test
    void getPostsByUserId_Success() {
        List<Post> posts = List.of(new Post(), new Post());

        when(postRepository.findByUser_UserId(1L)).thenReturn(posts);
        when(postMapper.convertToDTO(any(Post.class))).thenReturn(new PostDTO());

        List<PostDTO> postDTOS = postService.getPostsByUserId(1L);

        assertEquals(2, postDTOS.size());
        verify(postRepository, times(1)).findByUser_UserId(1L);
    }

    @Test
    void getPostsByBreed_Success() {
        List<Post> posts = List.of(new Post(), new Post());

        when(postRepository.findByPetBreed_PetBreedId(1L)).thenReturn(posts);
        when(postMapper.convertToDTO(any(Post.class))).thenReturn(new PostDTO());

        List<PostDTO> postDTOS = postService.getPostsByBreed(1L);

        assertEquals(2, postDTOS.size());
        verify(postRepository, times(1)).findByPetBreed_PetBreedId(1L);
    }
}
