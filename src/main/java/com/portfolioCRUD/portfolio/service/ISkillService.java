package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.Skill;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISkillService {

    void saveSkill(Skill skill);

    void deleteSkill(Long id);

    Skill findSkill(Long id);

    List<Skill> getSkills();

    List<Skill> getSkillsByOrderByPositionAsc();

}
