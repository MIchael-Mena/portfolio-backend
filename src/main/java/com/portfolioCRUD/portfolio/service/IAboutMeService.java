package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.AboutMe;
import org.springframework.stereotype.Service;

@Service
public interface IAboutMeService {

    AboutMe getAboutMe();

    void saveAboutMe(AboutMe aboutMe);

    void createAboutMe(AboutMe aboutMe);

    Long countAboutMe();

}
