package com.exashare.Exashare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "Resena_Herramienta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenaHerramienta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idResenaHerramienta;

    @Column(length = 2, nullable = false)
    private Integer puntuacion;

    @Column(length = 255, nullable = true)
    private String comentario;

    @Column(nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "idHerramienta", nullable = false)
    private Herramienta idHerramienta_fk;

}
