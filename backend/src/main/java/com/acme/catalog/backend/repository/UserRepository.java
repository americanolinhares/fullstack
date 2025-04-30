package com.acme.catalog.backend.repository;

import com.acme.catalog.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

  boolean existsByUsername(String username);
}
