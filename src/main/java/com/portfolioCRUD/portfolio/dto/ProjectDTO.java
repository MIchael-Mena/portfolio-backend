package com.portfolioCRUD.portfolio.dto;

import com.portfolioCRUD.portfolio.model.ImageOfProject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @NotBlank
    private String date;
    private String link;
    private String githubLink;
    private List<String> technologies;
    @NotNull
    private List<ImageOfProject> images;


}
