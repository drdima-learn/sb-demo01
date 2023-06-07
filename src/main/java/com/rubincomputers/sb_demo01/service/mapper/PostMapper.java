package com.rubincomputers.sb_demo01.service.mapper;

import com.rubincomputers.sb_demo01.model.Post;
import com.rubincomputers.sb_demo01.service.dto.PostDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {
    private PostMapper() {
    }

    public static List<PostDTO> dto(List<Post> posts) {
        return posts.stream().map(u -> dto(u)).collect(Collectors.toList());
    }

    public static PostDTO dto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .text(post.getText())
                .build();
    }
}
