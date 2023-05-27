package com.rubincomputers.sb_demo01.repository;

import com.rubincomputers.sb_demo01.model.User;
import org.apache.catalina.mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :id")
    int delete(Long id);

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("DELETE FROM User u WHERE u.email = ?1")
    int deleteByEmail(String email);

}
