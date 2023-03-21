package com.portfolioCRUD.portfolio.security.repository;

import com.portfolioCRUD.portfolio.security.entity.Rol;
import com.portfolioCRUD.portfolio.security.enums.RolName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByRolName(RolName rolName);


}
