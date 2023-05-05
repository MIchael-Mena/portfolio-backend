package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.Education;
import com.portfolioCRUD.portfolio.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService implements IEducationService {

    @Autowired
    private EducationRepository educationRepository;


    @Override
    public List<Education> getEducations() {
        return educationRepository.findAll();
    }

    @Override
    public List<Education> getEducationsByOrderByPositionAsc() {
        return educationRepository.findAllByOrderByPositionAsc();
    }

    @Override
    public List<Education> getEducationsByOrderByInitialDateDesc() {
        return educationRepository.findAllByOrderByInitialDateDesc();
    }

    @Override
    public void saveEducation(Education education) {
        educationRepository.save(education);
    }

    @Override
    public void deleteEducation(Long id) {
        educationRepository.deleteById(id);
    }

    @Override
    public Education findEducation(Long id) {
        return educationRepository.findById(id).orElse(null);
    }

    public boolean existsById(Long id) {
        return educationRepository.existsById(id);
    }
}
