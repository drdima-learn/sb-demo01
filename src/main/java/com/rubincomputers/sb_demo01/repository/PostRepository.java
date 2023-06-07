package com.rubincomputers.sb_demo01.repository;

import com.rubincomputers.sb_demo01.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
