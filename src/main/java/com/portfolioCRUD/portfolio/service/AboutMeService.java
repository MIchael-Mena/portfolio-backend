package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.AboutMe;
import com.portfolioCRUD.portfolio.repository.AboutMeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutMeService implements IAboutMeService {

    final
    AboutMeRepository aboutMeRepository;

    @Autowired
    public AboutMeService(AboutMeRepository aboutMeRepository) {
        this.aboutMeRepository = aboutMeRepository;
    }

    @Override
    public void saveAboutMe(AboutMe aboutMe) {
        aboutMeRepository.save(aboutMe);
    }

    @Override
    public AboutMe getAboutMe() {
        return aboutMeRepository.findAll().get(0);
    }

    @Override
    public void createAboutMe(AboutMe aboutMe) {
        aboutMeRepository.save(aboutMe);
    }

    @Override
    public Long countAboutMe() {
        return aboutMeRepository.count();
    }

}
