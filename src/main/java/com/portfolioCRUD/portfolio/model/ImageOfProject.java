package com.portfolioCRUD.portfolio.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class ImageOfProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @JsonIgnore
    private Project project;
    @NotNull
    private String original;
    @NotNull
    private String thumbnail;
    @Column(length = 410)
    private String description;
    private Integer position;


}
