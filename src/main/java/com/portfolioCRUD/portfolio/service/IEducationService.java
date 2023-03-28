package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.Education;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IEducationService {

    List<Education> getEducations();

    List<Education> getEducationsByOrderByPositionAsc();

    List<Education> getEducationsByOrderByInitialDateDesc();

    void saveEducation(Education education);

    void deleteEducation(Long id);

    Education findEducation(Long id);

}
