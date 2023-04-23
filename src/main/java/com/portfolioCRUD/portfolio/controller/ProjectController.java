package com.portfolioCRUD.portfolio.controller;

import com.portfolioCRUD.portfolio.dto.Message;
import com.portfolioCRUD.portfolio.dto.ProjectDTO;
import com.portfolioCRUD.portfolio.model.ImageOfProject;
import com.portfolioCRUD.portfolio.model.Project;
import com.portfolioCRUD.portfolio.service.ImageOfProjectService;
import com.portfolioCRUD.portfolio.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ImageOfProjectService imageOfProjectService;

    @GetMapping("")
    public ResponseEntity<List<ProjectDTO>> getProjects(@RequestParam(required = false) String _sort,
                                                        @RequestParam(required = false) String _order){
        if (_sort != null && _order != null) {
            if (_sort.equals("date") && _order.equals("desc")) {
                List<Project> projectList = this.projectService.getProjectsOrderByDateDesc();
                return ResponseEntity.ok( this.generateProjectsDTO(projectList) );
            }
        }
        List<Project> projectList = this.projectService.getProjects();
        return ResponseEntity.ok( this.generateProjectsDTO(projectList) );
    }

    private List<ProjectDTO> generateProjectsDTO(List<Project> projectList) {
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        projectList.forEach(project -> projectDTOList.add(this.getProjectDTO(project)));
        return projectDTOList;
    }

    private ProjectDTO getProjectDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setDate(project.getDate());
        projectDTO.setImages(this.imageOfProjectService.getImagesOfProjectByProjectId(project.getId()));
        projectDTO.setLink(project.getLink());
        projectDTO.setGithubLink(project.getGithubLink());
        String technologies = project.getTechnologies();
        String[] technologiesArray = technologies.split(",");
        projectDTO.setTechnologies(List.of(technologiesArray));
        return projectDTO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> findProject(@PathVariable Long id) {
        Project project = this.projectService.findProject(id);
        return ResponseEntity.ok(this.getProjectDTO(project));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> createProject(@NotBlank @Valid @RequestBody ProjectDTO projectDTO) {
        Project project = new Project();
        this.saveProject(projectDTO, project);
        // En este punto ya tenemos el id del proyecto creado
        projectDTO.getImages().forEach(image -> {
            image.setProject(project);
            this.imageOfProjectService.saveImageOfProject(image);
        });
        return ResponseEntity.ok(this.getProjectDTO(project));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteProject(@PathVariable Long id) {
        if (!this.projectService.existsProjectById(id)) {
            return ResponseEntity.badRequest().body(new Message("Project not found"));
        }
        this.imageOfProjectService.deleteImagesOfProjectByProjectId(id);
        this.projectService.deleteProject(id);
        return ResponseEntity.ok(new Message("Project deleted"));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editProject(@PathVariable Long id, @NotBlank @Valid @RequestBody ProjectDTO projectDTO) {
        if (!this.projectService.existsProjectById(id)) {
            return ResponseEntity.badRequest().body(new Message("Project not found"));
        }
        try {
            // Utilizo currentImages para comparar y eliminar las que no estén en projectDTO.getImages()
            List<ImageOfProject> currentImages = this.imageOfProjectService.getImagesOfProjectByProjectId(id);
            Project project = this.projectService.findProject(id);
            this.saveProject(projectDTO, project);
            this.updateImages(projectDTO, currentImages, project);
            this.deleteImages(currentImages);
            return ResponseEntity.ok(this.getProjectDTO(project));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Message("Error: " + e.getMessage()));
        }
    }

    private void deleteImages(List<ImageOfProject> currentImages) {
        currentImages.forEach(image -> this.imageOfProjectService.deleteImageOfProject(image.getId()));
    }

    private void updateImages(ProjectDTO projectDTO, List<ImageOfProject> currentImages, Project project) {
        projectDTO.getImages().forEach(image -> {
            ImageOfProject imageOfProject;
            if(image.getId() == null) {
                // Si el id es null, significa que es una nueva imagen
                imageOfProject = new ImageOfProject();
            } else {
                imageOfProject = this.imageOfProjectService.findImageOfProject(image.getId());
                currentImages.remove(imageOfProject);
            }
            imageOfProject.setProject(project);
            imageOfProject.setOriginal(image.getOriginal());
            imageOfProject.setMedium(image.getMedium());
            imageOfProject.setThumbnail(image.getThumbnail());
            if (image.getDescription() != null) {
                imageOfProject.setDescription(image.getDescription());
            }
            if (image.getPosition() != null) {
                imageOfProject.setPosition(image.getPosition());
            }
            if(image.getDeleteUrl() != null) {
                imageOfProject.setDeleteUrl(image.getDeleteUrl());
            }
            this.imageOfProjectService.saveImageOfProject(imageOfProject);
        });
    }

    private void saveProject(ProjectDTO projectDTO, Project project) {
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setDate(projectDTO.getDate());
        // Estos valores pueden ser nulos
        if (projectDTO.getLink() != null) {
        project.setLink(projectDTO.getLink());
        }
        if (projectDTO.getGithubLink() != null) {
            project.setGithubLink(projectDTO.getGithubLink());
        }
        if (projectDTO.getTechnologies() != null) {
            String technologies = String.join(",", projectDTO.getTechnologies());
            project.setTechnologies(technologies);
        }
        this.projectService.saveProject(project);
    }


}
