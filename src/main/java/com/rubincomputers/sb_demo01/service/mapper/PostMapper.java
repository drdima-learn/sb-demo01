package com.rubincomputers.sb_demo01.service.mapper;

import com.rubincomputers.sb_demo01.model.Post;
import com.rubincomputers.sb_demo01.service.dto.PostDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {
    private PostMapper() {
    }

    public static List<PostDTO> toDto(List<Post> posts) {
        return posts.stream().map(u -> toDto(u)).collect(Collectors.toList());
    }

    public static PostDTO toDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .text(post.getText())
                .build();
    }

    public static Post toEntity(PostDTO dto) {
        return Post.builder()
                .id(dto.getId())
                .text(dto.getText())
                .build();
    }
}
