package com.exashare.Exashare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exashare.Exashare.model.Arriendo;
import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.model.Usuario;


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
    SELECT h.id_herramienta, h.nombre, COUNT(a.id_arriendo) AS cantidad_arriendos
    FROM Herramienta h
    LEFT JOIN Arriendo a ON h.id_herramienta = a.id_herramienta
    LEFT JOIN Resenas r ON a.id_arriendo = r.id_arriendo
    WHERE h.estado = :estado
    GROUP BY h.id_herramienta, h.nombre
    """, nativeQuery = true)
    List<Object[]> obtenerHerramientasConCantidadDeArriendosYResenas();

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

    List<Herramienta> findByUsuario(Usuario usuario);

    List<Herramienta> findByPrecioHerramientaBetween(Integer precioMinimo, Integer precioMaximo);
    
}
