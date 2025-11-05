package com.svalero.music.rights.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "works")

public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column (nullable = false, unique = true)
    private String isrc;
    @Column
    private String genre;
    @Column
    private double duration;

    @ManyToMany //MUCHOS A MUCHOS
    @JoinTable(
            name = "musician_work",
            joinColumns = @JoinColumn(name = "work_id"),
            inverseJoinColumns = @JoinColumn(name = "musician_id")
    )
    //SE CREA LA TABLA JOIN ENTRE MUCHOS A MUCHOS

    private List<Musician> musicians; //LISTA DE MUSICOS
}
