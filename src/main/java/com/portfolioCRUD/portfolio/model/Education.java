package com.portfolioCRUD.portfolio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String job;
    private String company;

    @Column(length = 410)
    private String description;

    @Column(nullable = false, columnDefinition = "DATE")
    private String initialDate;
    @Column(columnDefinition = "DATE")
    private String finalDate;
    private String link;

    @Column(nullable = false)
    private Integer position;

}
