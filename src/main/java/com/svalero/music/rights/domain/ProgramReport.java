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
@Table(name= "program_reports")
public class ProgramReport {

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

    @ManyToOne
    @JoinColumn(name= "declarant_id")

    private Musician declarant;
    //Lista de musicos para el pdf

    @ManyToMany
    @JoinTable(
            name = "program_report_works",
            joinColumns = @JoinColumn (name = "program_report_id"),
            inverseJoinColumns = @JoinColumn (name = "work_id")
    )

    //Se crea tabla N a N donde un pdf puede tener MUCHAS obras y una obra puede estar en muchos PDFS

    private List<Work> works;
    //Lista de obras para el pdf.

    //Las referencias de las listas las detecta porque estan declaradas como ENTITY
}