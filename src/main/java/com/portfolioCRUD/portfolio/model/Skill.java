package com.portfolioCRUD.portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private Integer level;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private Integer position;
    @NotBlank
    @NotNull
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String icon;

}
