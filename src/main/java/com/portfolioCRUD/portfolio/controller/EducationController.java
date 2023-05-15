package com.portfolioCRUD.portfolio.controller;

import com.portfolioCRUD.portfolio.dto.Message;
import com.portfolioCRUD.portfolio.exception.ResourceNotFoundException;
import com.portfolioCRUD.portfolio.model.Education;
import com.portfolioCRUD.portfolio.service.EducationService;
import jakarta.validation.Valid;
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
    private EducationService educationService;

    @GetMapping("")
    public ResponseEntity<List<Education>> getEducations(@RequestParam(required = false) String _sort,
                                                         @RequestParam(required = false) String _order) {
        if (_sort != null && _order != null) {
            if (_sort.equals("initialDate") && _order.equals("asc")) {
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
    public ResponseEntity<Education> saveEducation(@Valid @RequestBody Education education) {
        educationService.saveEducation(education);
        return ResponseEntity.ok(education);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteEducation(@PathVariable Long id) {
        this.checkResource(id, (!educationService.existsById(id)) );
        educationService.deleteEducation(id);
        return ResponseEntity.ok(new Message("Education deleted"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editEducation(@PathVariable Long id, @Valid @RequestBody Education education) {
        Education educationToUpdate = educationService.findEducation(id);
        this.checkResource(id, (educationToUpdate == null) );
        educationToUpdate.setTitle(education.getTitle());
        educationToUpdate.setInstitution(education.getInstitution());
        educationToUpdate.setInitialDate(education.getInitialDate());
        educationToUpdate.setFinalDate(education.getFinalDate());
        educationService.saveEducation(educationToUpdate);
        return ResponseEntity.ok(new Message("Education edited"));
    }

    private void checkResource(Long id, boolean condition) {
        if (condition) {
            throw new ResourceNotFoundException(id.toString());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Message> updateEducation(@PathVariable Long id, @RequestBody Education education) {
        Education educationToUpdate = educationService.findEducation(id);
        this.checkResource(id, (educationToUpdate == null) );
        if (education.getTitle() != null) {
            educationToUpdate.setTitle(education.getTitle());
        }
        if (education.getInstitution() != null) {
            educationToUpdate.setInstitution(education.getInstitution());
        }
        if (education.getInitialDate() != null) {
            educationToUpdate.setInitialDate(education.getInitialDate());
        }if (education.getFinalDate() != null) {
            educationToUpdate.setFinalDate(education.getFinalDate());
        }
        educationService.saveEducation(educationToUpdate);
        return ResponseEntity.ok(new Message("Education updated"));
    }


}
