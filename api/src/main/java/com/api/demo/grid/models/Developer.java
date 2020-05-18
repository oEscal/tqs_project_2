package com.api.demo.grid.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Set<Game> games;
}
