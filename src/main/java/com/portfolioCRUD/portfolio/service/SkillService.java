package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.dto.SkillName;
import com.portfolioCRUD.portfolio.model.Skill;
import com.portfolioCRUD.portfolio.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService implements ISkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public void saveSkill(Skill skill) {
        this.skillRepository.save(skill);
    }

    @Override
    public void deleteSkill(Long id) {
        this.skillRepository.deleteById(id);
    }

    @Override
    public Skill findSkill(Long id) {
        return this.skillRepository.findById(id).get();
    }

    @Override
    public List<Skill> getSkills() {
        return this.skillRepository.findAll();
    }

    @Override
    public List<Skill> getSkillsByOrderByPositionAsc() {
        return this.skillRepository.findAllByOrderByPositionAsc();
    }

    public List<SkillName> getSkillNames() {
        return this.skillRepository.selectAllNames();
    }

    public boolean existsById(Long id) {
        return this.skillRepository.existsById(id);
    }
}
