package com.portfolioCRUD.portfolio.controller;

import com.portfolioCRUD.portfolio.dto.Message;
import com.portfolioCRUD.portfolio.model.AboutMe;
import com.portfolioCRUD.portfolio.service.AboutMeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;

@RestController
@RequestMapping("/aboutMe")
public class AboutMeController {

    @Autowired
    AboutMeService aboutMeService;

    @GetMapping("")
    public ResponseEntity<AboutMe> getAboutMe() {
        return ResponseEntity.ok(aboutMeService.getAboutMe());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update")
    public ResponseEntity<Message> updateAboutMe(@RequestBody AboutMe aboutMe) {
        if (this.checkAboutMeFields(aboutMe)) {
            AboutMe aboutMeToUpdate = this.aboutMeService.getAboutMe();
            this.updateAboutMeFields(aboutMe, aboutMeToUpdate);
            this.aboutMeService.saveAboutMe(aboutMeToUpdate);
            return ResponseEntity.ok(new Message("About me updated"));
        } else {
            return ResponseEntity.badRequest().body(new Message("You must provide at least one field to update"));
        }
    }

    private void updateAboutMeFields(AboutMe aboutMe, AboutMe aboutMeToUpdate) {
        if(aboutMe.getName() != null) {
            aboutMeToUpdate.setName(aboutMe.getName());
        }
        if ( aboutMe.getDescription() != null) {
            aboutMeToUpdate.setDescription(aboutMe.getDescription());
        }
        if ( aboutMe.getPhoto() != null) {
            aboutMeToUpdate.setPhoto(aboutMe.getPhoto());
        }
        if ( aboutMe.getTitle() != null) {
            aboutMeToUpdate.setTitle(aboutMe.getTitle());
        }
    }

    private boolean checkAboutMeFields(AboutMe aboutMe) {
//        return aboutMe.getName() == null && aboutMe.getDescription() == null &&
//                aboutMe.getPhoto() == null && aboutMe.getTitle() == null;
//        Field[] fields = AboutMe.class.getDeclaredFields();
        Field[] fields = aboutMe.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("id")) {
                continue; // Ignora el campo 'id'
            }
            field.setAccessible(true);
            Object fieldValue;
            try {
                fieldValue = field.get(aboutMe);
            } catch (IllegalAccessException e) {
                return false;
            }
            if (fieldValue != null) {
                return true;
            }
        }
        return false;
    }

}
