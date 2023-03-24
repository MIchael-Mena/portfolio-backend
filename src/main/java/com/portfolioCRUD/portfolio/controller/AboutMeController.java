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
    public AboutMe getAboutMe() {
        return aboutMeService.getAboutMe();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update")
    public ResponseEntity<Message> updateAboutMe(@RequestBody AboutMe aboutMe) {
        AboutMe aboutMeToUpdate = aboutMeService.getAboutMe();
        if(aboutMe.getName() == null) {
            aboutMeToUpdate.setName(aboutMeService.getAboutMe().getName());
        } else if (aboutMe.getTitle() == null) {
            aboutMeToUpdate.setTitle(aboutMeService.getAboutMe().getTitle());
        } else if (aboutMe.getDescription() == null) {
            aboutMeToUpdate.setDescription(aboutMeService.getAboutMe().getDescription());
        } else if (aboutMe.getPhoto() == null) {
            aboutMeToUpdate.setPhoto(aboutMeService.getAboutMe().getPhoto());
        }
        aboutMeService.saveAboutMe(aboutMeToUpdate);
        return ResponseEntity.ok( new Message("About me updated"));
    }

}
