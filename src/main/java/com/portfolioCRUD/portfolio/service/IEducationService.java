package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.Education;

import java.util.List;

public interface IEducationService {

    public List<Education> getEducations();

    public List<Education> getEducationsByOrderByPositionAsc();

    public void saveEducation(Education education);

    public void deleteEducation(Long id);

    public Education findEducation(Long id);

}
