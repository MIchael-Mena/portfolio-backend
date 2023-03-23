package com.portfolioCRUD.portfolio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;
    private String institution;

    @Column(length = 410)
    private String description;

    @Column(nullable = false, columnDefinition = "DATE")
    private String initialDate;

    @Column(columnDefinition = "DATE")
    private String finalDate;
    private String link;

    @Column(nullable = false)
    private Integer position;

    public Work(String title, String institution, String description, String initialDate, String finalDate, String link, int position) {
        this.title = title;
        this.institution = institution;
        this.description = description;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.link = link;
        this.position = position;
    }

}
