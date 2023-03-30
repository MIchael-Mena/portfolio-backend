package com.portfolioCRUD.portfolio.controller;

import com.portfolioCRUD.portfolio.dto.Message;
import com.portfolioCRUD.portfolio.model.Education;
import com.portfolioCRUD.portfolio.service.EducationService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/educations")
public class EducationController {

    @Autowired
    EducationService educationService;

    @GetMapping("")
    public ResponseEntity<List<Education>> getEducations(@RequestParam(required = false) String _sort,
                                                         @RequestParam(required = false) String _order) {
        if (_sort != null && _order != null) {
            if (_sort.equals("position") && _order.equals("asc")) {
                return ResponseEntity.ok(educationService.getEducationsByOrderByPositionAsc());
            } else if (_sort.equals("position") && _order.equals("desc")) {
//                return ResponseEntity.ok(educationService.getEducationsByOrderByPositionDesc());
            } else if (_sort.equals("initialDate") && _order.equals("asc")) {
//                return ResponseEntity.ok(educationService.getEducationsByOrderByInitialDateAsc());
            } else if (_sort.equals("initialDate") && _order.equals("desc")) {
                return ResponseEntity.ok(educationService.getEducationsByOrderByInitialDateDesc());
            } else if (_sort.equals("finalDate") && _order.equals("asc")) {
//                return ResponseEntity.ok(educationService.getEducationsByOrderByFinalDateAsc());
            } else if (_sort.equals("finalDate") && _order.equals("desc")) {
//                return ResponseEntity.ok(educationService.getEducationsByOrderByFinalDateDesc());
            }
        }
        return ResponseEntity.ok(educationService.getEducations());
    }

    @GetMapping("/{id}")
    public Education findEducation(@PathVariable Long id) {
        return educationService.findEducation(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Education> saveEducation(@RequestBody Education education) {
        educationService.saveEducation(education);
        return ResponseEntity.ok(education);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);
        return ResponseEntity.ok(new Message("Education deleted"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Message> editEducation(@PathVariable Long id, @RequestBody Education education) {
        Education educationToUpdate = educationService.findEducation(id);
        educationToUpdate.setTitle(education.getTitle());
        educationToUpdate.setInstitution(education.getInstitution());
        educationToUpdate.setInitialDate(education.getInitialDate());
        educationToUpdate.setFinalDate(education.getFinalDate());
        educationToUpdate.setPosition(education.getPosition());
        educationService.saveEducation(educationToUpdate);
        return ResponseEntity.ok(new Message("Education edited"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Message> updateEducation(@PathVariable Long id, @RequestBody Education education) {
        Education educationToUpdate = educationService.findEducation(id);
        if (education.getTitle() != null) {
            educationToUpdate.setTitle(education.getTitle());
        } else if (education.getInstitution() != null) {
            educationToUpdate.setInstitution(education.getInstitution());
        } else if (education.getInitialDate() != null) {
            educationToUpdate.setInitialDate(education.getInitialDate());
        } else if (education.getFinalDate() != null) {
            educationToUpdate.setFinalDate(education.getFinalDate());
        } else if (education.getPosition() != null) {
            educationToUpdate.setPosition(education.getPosition());
        }
        educationService.saveEducation(educationToUpdate);
        return ResponseEntity.ok(new Message("Education updated"));
    }


}
