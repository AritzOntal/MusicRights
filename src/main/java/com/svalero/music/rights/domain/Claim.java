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
@Table (name = "claims")

public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;
    @Column
    private String reference;
    @Column
    private String status;
    @Column
    private String type;
    @Column
    private String description;

    @ManyToOne
    @JoinColumn (name = "musician_id")

    private Musician musician;

}
