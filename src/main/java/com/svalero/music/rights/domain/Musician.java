package com.svalero.music.rights.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "musicians")

public class Musician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column (name = "birth_date")
    private LocalDate birthDate;
    @Column
    private boolean affiliated;
    @Column(nullable = false, unique = true, length = 12) //CONTROLAREMOS CON VALIDACIONS Y EXCEPCIONES
    private String dni;
    @Column (name = "affiliated_number")
    private long affiliatedNumber;
    //TODO RELACIONAR CON UNA LISTA DE WORKS POR MUSICO (List<Work)

    //NO VUELVO A CREAR LA TABLA, UTIULIZO MAPPER
    @ManyToMany(mappedBy = "musicians")
    private List<Work> works;

    @OneToMany(mappedBy = "musician")
    private List <Claim> claims;
}
