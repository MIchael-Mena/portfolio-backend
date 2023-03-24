package com.portfolioCRUD.portfolio.model;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String title;
    @Column(length = 410)
    private String description;

    @Column(nullable = false)
    private String institution;
    @Column(nullable = false, columnDefinition = "DATE")
    private String initialDate;
    @Column(columnDefinition = "DATE")
    private String finalDate;
    private String link;
    @Column(nullable = false)
    private Integer position;

}
