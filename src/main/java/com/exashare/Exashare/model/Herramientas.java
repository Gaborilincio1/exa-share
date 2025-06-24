package com.exashare.Exashare.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Herramientas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Herramientas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHerramientas;

    @ManyToOne
    @JoinColumn(name = "idArriendo",nullable = false)
    private Arriendo arriendo;

    @ManyToOne
    @JoinColumn(name = "idHerramienta",nullable = false)
    private Herramienta herramienta;

}
