package com.portfolioCRUD.portfolio.controller;

import com.portfolioCRUD.portfolio.dto.Message;
import com.portfolioCRUD.portfolio.dto.SkillName;
import com.portfolioCRUD.portfolio.exception.ResourceNotFoundException;
import com.portfolioCRUD.portfolio.model.Skill;
import com.portfolioCRUD.portfolio.service.SkillService;
import jakarta.validation.Valid;
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
    private SkillService skillService;

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
        this.checkResource(id, (!skillService.existsById(id)) );
        return ResponseEntity.ok(skillService.findSkill(id));
    }

    @GetMapping("/names")
    public ResponseEntity<List<SkillName>> getSkillNames() {
        return ResponseEntity.ok(skillService.getSkillNames());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Skill> saveSkill(@Valid @RequestBody Skill skill) {
        skillService.saveSkill(skill);
        return ResponseEntity.ok(skill);
    }

    private void checkResource(Long id, boolean condition) {
        if (condition) {
            throw new ResourceNotFoundException(id.toString());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteSkill(@PathVariable Long id) {
        this.checkResource(id, (!skillService.existsById(id)) );
        skillService.deleteSkill(id);
        return ResponseEntity.ok(new Message("Skill deleted"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Skill> editSkill(@PathVariable Long id, @Valid @RequestBody Skill skill) {
        Skill skillToUpdate = skillService.findSkill(id);
        this.checkResource(id, (skillToUpdate == null) );
        skillToUpdate.setName(skill.getName());
        skillToUpdate.setLevel(skill.getLevel());
        skillToUpdate.setPosition(skill.getPosition());
        skillToUpdate.setIcon(skill.getIcon());
        skillService.saveSkill(skillToUpdate);
        // saveSkill() le asigna un id al skill que se le pasa como parámetro
        return ResponseEntity.ok(skillToUpdate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
        Skill skillToUpdate = skillService.findSkill(id);
        this.checkResource(id, (skillToUpdate == null) );
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
