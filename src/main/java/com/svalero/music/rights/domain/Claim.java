package com.svalero.music.rights.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "claims")

public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La referencia no puede ser nula")
    @Column
    private String reference;

    @Column
    private String status;

    @Column
    private String type;

    @Column
    private String description;

    @Column
    private boolean pending;


    //TODO INTEGRAR FLOAT
    /*@DecimalMin(value = "1.0", message = "El valor no puede ser inferior a 1")
    @DecimalMax(value = "5.0", message = "El valor no puede ser mas de 5")
    @Column
    private float priorityLeve;*/

    @ManyToOne
    @JoinColumn(name = "musician_id")
    private Musician musician;

}
