package com.rubincomputers.sb_demo01.repository;

import com.rubincomputers.sb_demo01.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> getByUserId(long userId, Pageable pageable);

    Optional<Post> getByIdAndUserId(long postId, long userId);

}
