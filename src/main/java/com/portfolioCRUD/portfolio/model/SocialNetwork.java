package com.portfolioCRUD.portfolio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
public class SocialNetwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String link;
    @Column(columnDefinition = "LONGTEXT")
    private String icon;
    private Integer position;

    public SocialNetwork(String name, String link, String icon, int position) {
        this.name = name;
        this.link = link;
        this.icon = icon;
        this.position = position;
    }



}
