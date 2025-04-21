package com.natixis.catalog.backend.repository;

import com.natixis.catalog.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsername(String username);

  boolean existsByUsername(String username);
}
