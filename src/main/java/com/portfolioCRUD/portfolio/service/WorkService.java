package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.Work;
import com.portfolioCRUD.portfolio.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService implements IWorkService {

    @Autowired
    private WorkRepository workRepository;

    @Override
    public List<Work> getWorks() {
        return workRepository.findAll();
    }
    
    @Override
    public List<Work> getWorksByOrderByInitialDateDesc() {
        return workRepository.findAllByOrderByInitialDateDesc();
    }

    @Override
    public void saveWork(Work work) {
        workRepository.save(work);
    }

    @Override
    public void deleteWork(Long id) {
        workRepository.deleteById(id);
    }

    @Override
    public Work findWork(Long id) {
        return workRepository.findById(id).orElse(null);
    }

    public boolean existsWork(Long id) {
        return workRepository.existsById(id);
    }
}
