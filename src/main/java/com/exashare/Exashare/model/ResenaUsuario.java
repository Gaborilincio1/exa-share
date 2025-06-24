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
@Table(name = "Resena_Usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idResenaUsuario;

    @Column(length = 2, nullable = false)
    private Integer puntuacion;

    @Column(length = 255, nullable = true)
    private String comentario;

    @Column(nullable = false)
    private Date fecha;

    @OneToMany(mappedBy = "resenaUsuario", cascade = CascadeType.ALL)
    private Set<Resenas> resenas;
}
