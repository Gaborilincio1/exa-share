package com.exashare.Exashare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exashare.Exashare.model.Arriendo;
import com.exashare.Exashare.model.Herramienta;


@Repository
public interface HerramientaRepository extends JpaRepository<Herramienta, Long> {

    @Query(value = """
        SELECT h.id_herramienta, h.nombre, h.categoria, h.estado, h.precio_herramienta, h.ubicacion, u.nombre_usuario 
        FROM Herramienta h
        JOIN Usuario u ON h.id_usuario = u.id_usuario
        WHERE h.estado = :estado
        """, nativeQuery = true)
    List<Object[]> obtenerHerramientasYUsuarioPorEstado(@Param("estado") String estado);

    @Query(value = """
        SELECT
            h.nombre AS nombreHerramienta,
            ru.puntuacion,
            ru.comentario,
            ru.fecha,
            u.nombre_usuario AS nombreUsuario
        FROM herramienta h
        JOIN resena_herramienta rh ON rh.id_herramienta = h.id_herramienta
        JOIN resena_usuarios ru ON ru.id_resena_usuario = rh.id_resena_herramienta
        JOIN resenas r ON r.id_resena_usuario = ru.id_resena_usuario
        JOIN usuario u ON u.id_usuario = r.id_usuario
        """, nativeQuery = true)
    List<Object[]> obtenerResenasHerramientasConUsuarios();

    @Query(value = """
        SELECT h.id_herramienta, h.nombre, h.categoria, h.estado, h.precio_herramienta, h.ubicacion, h.id_usuario
        FROM Herramienta h       
        WHERE h.categoria = :categoria
        """, nativeQuery = true)
    List<Object[]> obtenerHerramientasPorCategoria(@Param("categoria") String categoria);

    @Query("""
    SELECT a FROM Arriendo a
    JOIN a.herramientas h
    WHERE h = :herramienta
    """)
    List<Arriendo> findByHerramienta(@Param("herramienta") Herramienta herramienta);


    List<Herramienta> findByPrecioHerramientaBetween(Integer precioMinimo, Integer precioMaximo);
    
}
