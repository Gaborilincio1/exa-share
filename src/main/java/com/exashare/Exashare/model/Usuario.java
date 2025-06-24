package com.exashare.Exashare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(length = 20, nullable = false)
    private String nombreUsuario;

    @Column(length = 50,nullable = false)
    private String email;

    @JsonIgnore
    @Column(length = 50, nullable = false)
    private String contraseña;

    @Column(length = 100, nullable = false)
    private String direccion;
    
    @Column(length = 20,nullable = false)
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "idMembresia",nullable = true)
    private Membresia idMembresia;

    @ManyToOne
    @JoinColumn(name = "idTipoUsuario",nullable = false)
    private TipoUsuario idTipoUsuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<Resenas> resenas;
}


