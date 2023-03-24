package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.Skill;
import com.portfolioCRUD.portfolio.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService implements ISkillService {

    @Autowired
    SkillRepository skillRepository;

    @Override
    public void saveSkill(Skill skill) {
        skillRepository.save(skill);
    }

    @Override
    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }

    @Override
    public Skill findSkill(Long id) {
        return skillRepository.findById(id).get();
    }

    @Override
    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    @Override
    public List<Skill> getSkillsByOrderByPositionAsc() {
        return skillRepository.findAllByOrderByPositionAsc();
    }



}
