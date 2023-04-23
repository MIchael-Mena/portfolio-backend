package com.portfolioCRUD.portfolio.repository;

import com.portfolioCRUD.portfolio.dto.SkillName;
import com.portfolioCRUD.portfolio.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findAllByOrderByPositionAsc();

    @Query(value = "SELECT name FROM skill", nativeQuery = true)
    List<SkillName> selectAllNames();

}
