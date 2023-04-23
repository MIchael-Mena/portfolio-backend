package com.portfolioCRUD.portfolio.security.repository;

import com.portfolioCRUD.portfolio.security.entity.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiRepository extends JpaRepository<Api, Long> {

    @Modifying
    int deleteByUserId(Long id);

    List<Api> findAllByUserId(Long id);

}
