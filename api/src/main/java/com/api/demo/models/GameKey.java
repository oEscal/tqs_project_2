package com.api.demo.models;

import javax.persistence.*;


@Entity
public class GameKey {

    @Id
    @GeneratedValue( strategy= GenerationType.AUTO )
    private int id;

    @Column(unique = true)
    private String gameKey;
}
