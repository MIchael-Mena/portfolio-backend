package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.Work;

import java.util.List;

public interface IWorkService {

    public List<Work> getWorks();

    public List<Work> getWorksByOrderByPositionAsc();

    public void saveWork(Work work);

    public void deleteWork(Long id);

    public Work findWork(Long id);

}
