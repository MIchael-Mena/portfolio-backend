package com.portfolioCRUD.portfolio.controller;

import com.portfolioCRUD.portfolio.dto.Message;
import com.portfolioCRUD.portfolio.model.AboutMe;
import com.portfolioCRUD.portfolio.service.AboutMeService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@NoArgsConstructor
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
        AboutMe aboutMeToUpdate = aboutMeService.getAboutMe();
        if(aboutMe.getName() != null) {
            aboutMeToUpdate.setName(aboutMe.getName());
        } else if ( aboutMe.getDescription() != null) {
            aboutMeToUpdate.setDescription(aboutMe.getDescription());
        } else if ( aboutMe.getPhoto() != null) {
            aboutMeToUpdate.setPhoto(aboutMe.getPhoto());
        } else if ( aboutMe.getTitle() != null) {
            aboutMeToUpdate.setTitle(aboutMe.getTitle());
        }
        aboutMeService.saveAboutMe(aboutMeToUpdate);
        return ResponseEntity.ok( new Message("About me updated"));
    }

}
