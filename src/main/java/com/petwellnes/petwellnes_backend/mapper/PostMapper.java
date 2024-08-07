package com.petwellnes.petwellnes_backend.mapper;

import com.petwellnes.petwellnes_backend.model.dto.postDto.PostCreateDTO;
import com.petwellnes.petwellnes_backend.model.dto.postDto.PostDTO;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final ModelMapper modelMapper;

    public PostMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(postCreateToPostMap);
        this.modelMapper.addMappings(postToPostDTOMap);
    }

    PropertyMap<PostCreateDTO, Post> postCreateToPostMap = new PropertyMap<PostCreateDTO, Post>() {
        protected void configure() {
            skip().setPostId(null);
        }
    };

    PropertyMap<Post, PostDTO> postToPostDTOMap = new PropertyMap<Post, PostDTO>() {
        protected void configure() {
            map().setPostId(source.getPostId());
            map().setPetBreedId(source.getPetBreed().getPetBreedId());
            map().setPetTypeId(source.getPetType().getPetTypeId());
            map().setUserId(source.getUser().getUserId());
            map().setTopicId(source.getTopic().getTopicId());
        }
    };

    public Post convertToEntity(PostCreateDTO postCreateDTO) {
        return modelMapper.map(postCreateDTO, Post.class);
    }

    public PostDTO convertToDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }
}
