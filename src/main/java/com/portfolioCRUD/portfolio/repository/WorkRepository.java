package com.portfolioCRUD.portfolio.repository;

import com.portfolioCRUD.portfolio.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    List<Work> findAllByOrderByPositionAsc();

    //Fecha inicial de mayor a menor
    List<Work> findAllByOrderByInitialDateDesc();



}
