package com.portfolioCRUD.portfolio.controller;

import com.portfolioCRUD.portfolio.dto.Message;
import com.portfolioCRUD.portfolio.model.Skill;
import com.portfolioCRUD.portfolio.service.SkillService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    SkillService skillService;

    @GetMapping("")
    public ResponseEntity<List<Skill>> getSkill(@RequestParam(required = false) String _sort,
                                @RequestParam(required = false) String _order) {
        if (_sort != null && _order != null) {
            if (_sort.equals("position") && _order.equals("asc")) {
                return ResponseEntity.ok(skillService.getSkillsByOrderByPositionAsc());
            }
        }
        return ResponseEntity.ok(skillService.getSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> findSkill(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.findSkill(id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Skill> saveSkill(@RequestBody Skill skill) {
        skillService.saveSkill(skill);
        return ResponseEntity.ok(skill);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok(new Message("Skill deleted"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Skill> editSkill(@PathVariable Long id, @RequestBody Skill skill) {
        Skill skillToUpdate = skillService.findSkill(id);
        skillToUpdate.setName(skill.getName());
        skillToUpdate.setLevel(skill.getLevel());
        skillToUpdate.setPosition(skill.getPosition());
        skillToUpdate.setIcon(skill.getIcon());
        skillService.saveSkill(skillToUpdate);
        return ResponseEntity.ok(skillToUpdate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
        Skill skillToUpdate = skillService.findSkill(id);
        if (skill.getName() != null) {
            skillToUpdate.setName(skill.getName());
        }
        if (skill.getLevel() != null) {
            skillToUpdate.setLevel(skill.getLevel());
        }
        if (skill.getPosition() != null) {
            skillToUpdate.setPosition(skill.getPosition());
        }
        if (skill.getIcon() != null) {
            skillToUpdate.setIcon(skill.getIcon());
        }
        skillService.saveSkill(skillToUpdate);
        return ResponseEntity.ok(skillToUpdate);
    }



}
