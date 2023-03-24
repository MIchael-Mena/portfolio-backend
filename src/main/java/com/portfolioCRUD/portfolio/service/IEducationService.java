package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.Education;

import java.util.List;

public interface IEducationService {

    List<Education> getEducations();

    List<Education> getEducationsByOrderByPositionAsc();

    void saveEducation(Education education);

    void deleteEducation(Long id);

    Education findEducation(Long id);

}
