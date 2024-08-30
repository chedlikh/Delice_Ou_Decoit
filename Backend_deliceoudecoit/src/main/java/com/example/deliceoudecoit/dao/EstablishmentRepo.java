package com.example.deliceoudecoit.dao;

import com.example.deliceoudecoit.entities.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstablishmentRepo extends JpaRepository<Establishment,Long> {
    Optional<Establishment> findEstablishmentByName(String name);
}
