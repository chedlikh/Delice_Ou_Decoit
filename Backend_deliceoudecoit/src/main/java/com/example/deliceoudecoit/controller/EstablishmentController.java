package com.example.deliceoudecoit.controller;

import com.example.deliceoudecoit.dao.EstablishmentRepo;
import com.example.deliceoudecoit.entities.AuthenticationResponse;
import com.example.deliceoudecoit.entities.Establishment;
import com.example.deliceoudecoit.entities.User;
import com.example.deliceoudecoit.service.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/establishment")
public class EstablishmentController {
    @Autowired
    private EstablishmentService establishmentService;
    @Autowired
    private EstablishmentRepo establishmentRepo;
    @PostMapping
    public ResponseEntity<Establishment> createEstablishment(@RequestBody Establishment establishment) {
        Establishment createdEstablishment = establishmentService.createEstablishment(establishment);
        return ResponseEntity.ok(createdEstablishment);
    }
    @GetMapping
    public ResponseEntity<List<Establishment>> getAllEstablishment(){
        List<Establishment> establishments=establishmentService.getAllEstablishment();
        return ResponseEntity.ok(establishments);
    }
    @GetMapping("/{name}")
    public ResponseEntity<Establishment> getEstablishmentByName(@PathVariable String name) {
        try {
            Establishment establishment = establishmentService.loadEstablishmentByName(name);
            return ResponseEntity.ok(establishment);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }


}
