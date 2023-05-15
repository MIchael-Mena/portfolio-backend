package com.portfolioCRUD.portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Column(length = 410)
    private String description;

    @NotNull
    @Column(columnDefinition = "DATE")
    private String date;

    private String link;
    private String githubLink;
    private String technologies;

}
