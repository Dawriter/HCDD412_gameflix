package com.example.gameflix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="accounts")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "username", unique = true, nullable = false)
    private String username;
    @Column(name= "email", unique = true, nullable = false)
    private String email;
    @Column(name= "password")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Game> games = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Long getId() {
        return id;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}

