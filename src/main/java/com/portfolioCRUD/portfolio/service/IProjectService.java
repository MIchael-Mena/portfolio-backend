package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.Project;

import java.util.List;

public interface IProjectService {

    List<Project> getProjectsOrderByDateDesc();

    void saveProject(Project project);

    void deleteProject(Long id);

    Project findProject(Long id);

}
