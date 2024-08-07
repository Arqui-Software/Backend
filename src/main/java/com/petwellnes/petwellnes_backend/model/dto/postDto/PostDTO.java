package com.petwellnes.petwellnes_backend.model.dto.postDto;

import com.petwellnes.petwellnes_backend.model.entity.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostDTO {
    private Long postId;
    private String title;
    private String content;
    private Long userId;
    private Long petTypeId;
    private String petTypeName;
    private Long petBreedId;
    private String petBreedName;
    private Long topicId;
    private String topicName;
    private LocalDateTime createdAt;

    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userId = post.getUser().getUserId();
        this.petTypeId = post.getPetType().getPetTypeId();
        this.petTypeName = post.getPetType().getName();
        this.petBreedId = post.getPetBreed().getPetBreedId();
        this.petBreedName = post.getPetBreed().getName();
        this.topicId = post.getTopic().getTopicId();
        this.topicName = post.getTopic().getName();
        this.createdAt = post.getCreatedAt();
    }
}
