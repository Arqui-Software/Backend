package com.petwellnes.petwellnes_backend.service.Impl;

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
import com.petwellnes.petwellnes_backend.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PetTypeRepository petTypeRepository;
    private final PetBreedRepository petBreedRepository;
    private final TopicRepository topicRepository;
    private final PostMapper postMapper;

    @Override
    @Transactional
    public PostDTO createPost(PostCreateDTO postCreateDTO) {
        User user = userRepository.findById(postCreateDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        PetType petType = petTypeRepository.findById(postCreateDTO.getPetTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de mascota no encontrado"));

        PetBreed petBreed = petBreedRepository.findById(postCreateDTO.getPetBreedId())
                .orElseThrow(() -> new EntityNotFoundException("Raza de mascota no encontrada"));

        Topic topic = topicRepository.findById(postCreateDTO.getTopicId())
                .orElseThrow(() -> new EntityNotFoundException("Tema no encontrado"));

        logger.info("Creating post with petTypeId: {}, petBreedId: {}, and topicId: {}", petType.getPetTypeId(), petBreed.getPetBreedId(), topic.getTopicId());
        if (!petBreed.getPetType().getPetTypeId().equals(petType.getPetTypeId())) {
            throw new IllegalArgumentException("La raza de mascota no corresponde al tipo de mascota.");
        }

        Post post = postMapper.convertToEntity(postCreateDTO);
        post.setUser(user);
        post.setPetType(petType);
        post.setPetBreed(petBreed);
        post.setTopic(topic);
        postRepository.save(post);
        return postMapper.convertToDTO(post);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post no encontrado"));
        return postMapper.convertToDTO(post);
    }

    @Override
    public List<PostDTO> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable).stream()
                .map(postMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostDTO updatePost(Long postId, PostUpdateDTO postUpdateDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post no encontrado"));

        PetType petType = petTypeRepository.findById(postUpdateDTO.getPetTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de mascota no encontrado"));

        PetBreed petBreed = petBreedRepository.findById(postUpdateDTO.getPetBreedId())
                .orElseThrow(() -> new EntityNotFoundException("Raza de mascota no encontrada"));

        Topic topic = topicRepository.findById(postUpdateDTO.getTopicId())
                .orElseThrow(() -> new EntityNotFoundException("Tema no encontrado"));

        logger.info("Updating post with petTypeId: {}, petBreedId: {}, and topicId: {}", petType.getPetTypeId(), petBreed.getPetBreedId(), topic.getTopicId());
        if (!petBreed.getPetType().getPetTypeId().equals(petType.getPetTypeId())) {
            throw new IllegalArgumentException("La raza de mascota no corresponde al tipo de mascota.");
        }

        post.setTitle(postUpdateDTO.getTitle());
        post.setContent(postUpdateDTO.getContent());
        post.setPetType(petType);
        post.setPetBreed(petBreed);
        post.setTopic(topic);
        postRepository.save(post);

        return postMapper.convertToDTO(post);
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post no encontrado"));
        postRepository.delete(post);
    }

    @Override
    public List<PostDTO> getRecentPosts() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Post> page = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return page.getContent().stream()
                .map(postMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Map<String, List<PostDTO>> getRecentPostsGroupedByType() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Post> page = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<Post> posts = page.getContent();

        return posts.stream()
                .map(postMapper::convertToDTO)
                .collect(Collectors.groupingBy(PostDTO::getPetTypeName));
    }

    @Override
    public List<PostDTO> getResponsesByParentPostId(Long parentId) {
        Post parentPost = postRepository.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException("Post padre no encontrado"));
        return postRepository.findByParentPost(parentPost).stream()
                .map(postMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByAnimalType(Long animalTypeId) {
        List<Post> posts = postRepository.findByPetType_PetTypeId(animalTypeId);
        return posts.stream()
                .map(postMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findByUser_UserId(userId);
        return posts.stream()
                .map(postMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByBreed(Long breedId) {
        List<Post> posts = postRepository.findByPetBreed_PetBreedId(breedId);
        return posts.stream()
                .map(postMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}