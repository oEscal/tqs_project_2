package com.api.demo.grid.models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @EqualsAndHashCode.Exclude
    private Set<Game> games;
}
