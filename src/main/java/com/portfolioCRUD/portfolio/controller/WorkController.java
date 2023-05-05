package com.portfolioCRUD.portfolio.controller;

import com.portfolioCRUD.portfolio.dto.Message;
import com.portfolioCRUD.portfolio.exception.ResourceNotFoundException;
import com.portfolioCRUD.portfolio.model.Work;
import com.portfolioCRUD.portfolio.service.WorkService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/works")
public class WorkController {

    @Autowired
    WorkService workService;

    @GetMapping("")
    public ResponseEntity<List<Work>> getWorks(@RequestParam(required = false) String _sort,
                                               @RequestParam(required = false) String _order) {
        if (_sort != null && _order != null) {
            if (_sort.equals("position") && _order.equals("asc")) {
                return ResponseEntity.ok(workService.getWorksByOrderByPositionAsc());
            } else if (_sort.equals("position") && _order.equals("desc")) {
//                return ResponseEntity.ok(workService.getWorksByOrderByPositionDesc());
            } else if (_sort.equals("initialDate") && _order.equals("asc")) {
//                return ResponseEntity.ok(workService.getWorksByOrderByInitialDateAsc());
            } else if (_sort.equals("initialDate") && _order.equals("desc")) {
                return ResponseEntity.ok(workService.getWorksByOrderByInitialDateDesc());
            } else if (_sort.equals("finalDate") && _order.equals("asc")) {
//                return ResponseEntity.ok(workService.getWorksByOrderByFinalDateAsc());
            } else if (_sort.equals("finalDate") && _order.equals("desc")) {
//                return ResponseEntity.ok(workService.getWorksByOrderByFinalDateDesc());
            }
        }
        return ResponseEntity.ok(workService.getWorks());
    }

    @GetMapping("/{id}")
    public Work findWork(@PathVariable Long id) {
        return workService.findWork(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Work> saveWork(@Valid @RequestBody Work work) {
        workService.saveWork(work);
        return ResponseEntity.ok(work);
    }

    private void checkResource(Long id, boolean condition) {
        if (condition) {
            throw new ResourceNotFoundException(id.toString());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteWork(@PathVariable Long id) {
        this.checkResource(id, !workService.existsWork(id));
        workService.deleteWork(id);
        return ResponseEntity.ok(new Message("Work deleted"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Message> editWork(@PathVariable Long id, @Valid @RequestBody Work work) {
        Work workToUpdate = workService.findWork(id);
        this.checkResource(id, (workToUpdate == null));
        workToUpdate.setJob(work.getJob());
        workToUpdate.setCompany(work.getCompany());
        workToUpdate.setDescription(work.getDescription());
        workToUpdate.setLink(work.getLink());
        workToUpdate.setPosition(work.getPosition());
        workService.saveWork(workToUpdate);
        return ResponseEntity.ok(new Message("Work edited"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Message> updateWork(@PathVariable Long id, @RequestBody Work work) {
        Work workToUpdate = workService.findWork(id);
        this.checkResource(id, (workToUpdate == null));
        if (work.getJob() != null) {
            workToUpdate.setJob(work.getJob());
        }
        if (work.getCompany() != null) {
            workToUpdate.setCompany(work.getCompany());
        }
        if (work.getDescription() != null) {
            workToUpdate.setDescription(work.getDescription());
        }
        if (work.getLink() != null) {
            workToUpdate.setLink(work.getLink());
        }
        if (work.getPosition() != null) {
            workToUpdate.setPosition(work.getPosition());
        }
        workService.saveWork(workToUpdate);
        return ResponseEntity.ok(new Message("Work updated"));
    }

}
