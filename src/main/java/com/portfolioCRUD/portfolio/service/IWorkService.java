package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.Work;

import java.util.List;

public interface IWorkService {

    List<Work> getWorks();

    List<Work> getWorksByOrderByPositionAsc();

    List<Work> getWorksByOrderByInitialDateDesc();

    void saveWork(Work work);

    void deleteWork(Long id);

    Work findWork(Long id);

}
