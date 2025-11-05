package com.svalero.music.rights.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "documents")

public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String type;
    @Column
    private String filename;
    @Column
    private Long size;
    @Column (name = "create_at")
    private LocalDate createAt;
    @Column
    private boolean complete;
    //TODO CREAR COLUMNA PARA GUARDAR ARRAY DE BYETS DEL PDF

    @OneToOne
    @JoinColumn(name = "concert_id, unique = true")
    private Concert concert;

}
