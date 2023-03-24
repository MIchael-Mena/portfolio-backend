package com.portfolioCRUD.portfolio.util;

import com.portfolioCRUD.portfolio.model.AboutMe;
import com.portfolioCRUD.portfolio.service.AboutMeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateAboutMe implements CommandLineRunner {

    @Autowired
    AboutMeService aboutMeService;

    @Override
    public void run(String... args) throws Exception {
        if(aboutMeService.countAboutMe() == 0) {
            AboutMe aboutMe = new AboutMe();
            aboutMe.setName("Your name");
            aboutMe.setTitle("Your title");
            aboutMe.setDescription("Your description");
            aboutMe.setPhoto("Your photo");
            aboutMeService.createAboutMe(aboutMe);
        }
    }

}
