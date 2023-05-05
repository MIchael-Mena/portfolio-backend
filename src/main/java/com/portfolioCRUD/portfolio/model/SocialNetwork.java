package com.portfolioCRUD.portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SocialNetwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private String link;
    @NotBlank
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    @NotNull
    private String icon;
    @NotBlank
    @NotNull
    @Column(nullable = false)
    private Integer position;

}
