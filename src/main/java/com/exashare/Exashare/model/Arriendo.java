package com.exashare.Exashare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Arriendo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Arriendo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idArriendo;

    @Column(nullable = false)
    private Date fechaInicio;

    @Column(nullable = false)
    private Date fechaFin;

    @Column(length = 30, nullable = false)
    private String estado;

    @Column(length = 15, nullable = false)
    private Integer precioArriendo;

    @OneToMany(mappedBy = "arriendo", cascade = CascadeType.ALL)
    private Set<Herramientas> herramientas;
}
