package com.example.deliceoudecoit.dao;

import com.example.deliceoudecoit.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Long> {
    List<Image> findByEstablishmentId(Long establishmentId);
}
