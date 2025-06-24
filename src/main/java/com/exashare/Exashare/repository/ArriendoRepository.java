package com.exashare.Exashare.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exashare.Exashare.model.Arriendo;
import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.model.Usuario;

@Repository
public interface ArriendoRepository extends JpaRepository<Arriendo, Long> {

    @Query(value = """
        SELECT u.nombre_usuario AS arrendador, h.nombre AS herramienta, a.id_arriendo, 
               a.fecha_inicio, a.fecha_fin, a.estado
        FROM usuario u
        JOIN herramienta h ON u.id_usuario = h.id_usuario
        JOIN herramientas hp ON h.id_herramienta = hp.id_herramienta
        JOIN arriendo a ON a.id_arriendo = hp.id_arriendo
        """, nativeQuery = true)
    List<Map<String, Object>> getHerramientasConUsuarioYArriendo();

    List<Arriendo> findByFechaInicioBetween(Date fechaInicio, Date fechaFin);

    List<Arriendo> findByEstadoAndFechaInicioBetween(String estado, Date fechaInicio, Date fechaFin);

    @Query("""
    SELECT a FROM Arriendo a
    JOIN a.herramientas h
    JOIN h.herramienta he
    WHERE he.idUsuario = :usuario
    """)
    List<Arriendo> findByUsuario(@Param("usuario") Usuario usuario);

    @Query("""
    SELECT a FROM Arriendo a
    JOIN a.herramientas h
    WHERE h.herramienta = :herramienta
    """)
    List<Arriendo> findByHerramienta(@Param("herramienta") Herramienta herramienta);

    

}
