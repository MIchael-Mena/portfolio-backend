package com.portfolioCRUD.portfolio.security.service;


import com.portfolioCRUD.portfolio.security.entity.Rol;
import com.portfolioCRUD.portfolio.security.enums.RolName;
import com.portfolioCRUD.portfolio.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolName(RolName rolName) {
        return rolRepository.findByRolName(rolName);
    }

    public void save(Rol rol) {
        rolRepository.save(rol);
    }

}
