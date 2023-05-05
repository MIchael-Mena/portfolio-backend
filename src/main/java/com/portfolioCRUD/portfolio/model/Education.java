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
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String title;
    @Column(length = 410)
    private String description;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String institution;
    @NotBlank
    @NotNull
    @Column(nullable = false, columnDefinition = "DATE")
    private String initialDate;
    @Column(columnDefinition = "DATE")
    private String finalDate;
    private String link;

    @Column(nullable = false)
    private Integer position;

}
