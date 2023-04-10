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
import org.springframework.validation.BindingResult;
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
        projectList.forEach(project -> {
            projectDTOList.add(this.getProjectDTO(project));
        });
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
        this.imageOfProjectService.deleteImagesOfProjectByProjectId(id);
        this.projectService.deleteProject(id);
        return ResponseEntity.ok(new Message("Project deleted"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> editProject(@PathVariable Long id, @NotBlank @Valid @RequestBody ProjectDTO projectDTO) {
        try {
            // TODO: si projectDTO.getImages() no tiene id, genera un error. Pero previamente se modifica el proyecto
            Project project = this.projectService.findProject(id);
            this.saveProject(projectDTO, project);
            // En este punto ya tenemos el id del proyecto creado
            projectDTO.getImages().forEach(image -> {
                ImageOfProject imageOfProject;
                imageOfProject = this.imageOfProjectService.findImageOfProject(image.getId());
                imageOfProject.setProject(project);
                imageOfProject.setOriginal(image.getOriginal());
                imageOfProject.setThumbnail(image.getThumbnail());
                if (image.getDescription() != null) {
                    imageOfProject.setDescription(image.getDescription());
                }
                if (image.getPosition() != null) {
                    imageOfProject.setPosition(image.getPosition());
                }
                this.imageOfProjectService.saveImageOfProject(imageOfProject);
            });
            return ResponseEntity.ok(this.getProjectDTO(project));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Message("Error: " + e.getMessage()));
        }
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
