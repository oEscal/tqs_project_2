package com.api.demo.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String name;

    private String email;

    private String password;

    private int age;

    @OneToMany
    private Set<Review> reviews;

    @OneToMany
    private Set<Report> reports;

    private String photoUrl;

    @OneToMany
    private Set<Game> wishlist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Review> getReviews() {
        return new HashSet<>(this.reviews);
    }

    public Set<Report> getReports() {
        return new HashSet<>(this.reports);
    }

    public String getPhotoUrl() { return photoUrl; }

    public void setPhotoUrl(String photo) { this.photoUrl = photo; }

}
