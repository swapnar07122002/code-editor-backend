package com.swapna.collabeditor.repository;

import com.swapna.collabeditor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //fetch email
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
