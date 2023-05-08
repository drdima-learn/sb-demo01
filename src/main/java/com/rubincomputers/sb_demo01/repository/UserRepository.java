package com.rubincomputers.sb_demo01.repository;

import com.rubincomputers.sb_demo01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
