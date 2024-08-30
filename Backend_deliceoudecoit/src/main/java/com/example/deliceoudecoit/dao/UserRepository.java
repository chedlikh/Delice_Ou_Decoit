package com.example.deliceoudecoit.dao;

import com.example.deliceoudecoit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    List<User> findByNumberphone(Integer numerotelephone);
    void deleteByUsername(String username);
}
