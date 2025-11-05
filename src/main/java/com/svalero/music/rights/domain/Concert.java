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
@Table(name= "concerts")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String showTitle;
    @Column
    private String city;
    @Column
    private String province;
    @Column
    private LocalDate date;
    @Column
    private String status;
    @Column
    private Double longitude;
    @Column
    private Double latitude;

    @ManyToOne
    @JoinColumn(name = "musician_id")

    private Musician musician;
}