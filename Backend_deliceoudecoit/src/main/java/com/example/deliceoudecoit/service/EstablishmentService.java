package com.example.deliceoudecoit.service;

import com.example.deliceoudecoit.dao.CategoryRepo;
import com.example.deliceoudecoit.dao.EstablishmentRepo;
import com.example.deliceoudecoit.dao.UserRepository;
import com.example.deliceoudecoit.entities.Category;
import com.example.deliceoudecoit.entities.Establishment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class EstablishmentService {
    @Autowired
    private EstablishmentRepo establishmentRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private UserRepository userRepo;

    public List<Establishment> getAllEstablishment(){return establishmentRepo.findAll();}
    public Optional<Establishment> getEstablishmentById(Long id){return establishmentRepo.findById(id);}

    public Establishment loadEstablishmentByName(String name) throws UsernameNotFoundException {
        return establishmentRepo.findEstablishmentByName(name)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
    public Establishment createEstablishment(Establishment establishment) {
        // Check if category exists
        if (establishment.getCategory() != null) {
            Category category = categoryRepo.findById(establishment.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            establishment.setCategory(category);
        } else {
            throw new RuntimeException("Category cannot be null");
        }

        // Check if user exists


        return establishmentRepo.save(establishment);
    }

    public Establishment UpdateEstablishment(Long id,Establishment UEstablishment){
        Establishment establishment=establishmentRepo.findById(id).orElseThrow(()->new RuntimeException(""));
        establishment.setName(UEstablishment.getName());
        establishment.setDescription(UEstablishment.getDescription());
        return establishmentRepo.save(establishment);
    }
    public void deleteEstablishment(Long id) {
        Establishment establishment = establishmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Establishment not found"));
        establishmentRepo.delete(establishment);
    }
}
