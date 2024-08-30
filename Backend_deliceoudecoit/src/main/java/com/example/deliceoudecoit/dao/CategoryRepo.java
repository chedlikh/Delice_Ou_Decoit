package com.example.deliceoudecoit.dao;

import com.example.deliceoudecoit.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
