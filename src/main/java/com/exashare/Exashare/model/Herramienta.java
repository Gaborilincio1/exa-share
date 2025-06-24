package com.exashare.Exashare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Herramienta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Herramienta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHerramienta;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 100, nullable = false)
    private String descripcion;

    @Column(length = 100, nullable = false)
    private String categoria;

    @Column(length = 30, nullable = false)
    private String estado;

    @Column(length = 100, nullable = false)
    private String ubicacion;

    @Column(length = 30, nullable = false)
    private Integer precioHerramienta;

    @ManyToOne
    @JoinColumn(name = "idUsuario",nullable = false)
    private Usuario idUsuario;

    @OneToMany(mappedBy = "herramienta", cascade = CascadeType.ALL)
    private Set<Herramientas> herramientas;
}
