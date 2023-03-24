package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.AboutMe;

public interface IAboutMeService {

    AboutMe getAboutMe();

    void saveAboutMe(AboutMe aboutMe);

    void createAboutMe(AboutMe aboutMe);

    Long countAboutMe();


}
