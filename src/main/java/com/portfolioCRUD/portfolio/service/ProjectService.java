package com.portfolioCRUD.portfolio.service;

import com.portfolioCRUD.portfolio.model.Project;
import com.portfolioCRUD.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void saveProject(Project project) {
        this.projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        this.projectRepository.deleteById(id);
    }

    @Override
    public Project findProject(Long id) {
        return this.projectRepository.findById(id).get();
    }

    @Override
    public List<Project> getProjectsOrderByDateDesc() {
        return this.projectRepository.findAllByOrderByDateDesc();
    }

    public List<Project> getProjects() {
        return this.projectRepository.findAll();
    }

}
