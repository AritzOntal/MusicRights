package com.svalero.music.rights.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String username;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String passwordHash;
    @Column (name = "register_date")
    private LocalDate registerDate;

    //TODO RELACIONAR CON ALGUNA TABLA EL USUARIO

    @OneToOne
    @JoinColumn (name = "musician_id")

    private Musician musician;
}
