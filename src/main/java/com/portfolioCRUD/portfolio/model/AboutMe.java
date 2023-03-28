package com.portfolioCRUD.portfolio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*@NoArgsConstructor
@AllArgsConstructor*/
@Getter @Setter
@Entity
public class AboutMe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String title;

    @NotNull
    @Column(length = 410)
    private String description;

    @NotNull
    @Column(columnDefinition = "LONGTEXT")
    private String photo;

    public AboutMe() {
    }

    public AboutMe(@NotNull String name, @NotNull String title, @NotNull String description, @NotNull String photo) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.photo = photo;
    }


}
