package com.exashare.Exashare.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exashare.Exashare.model.Membresia;
import com.exashare.Exashare.model.TipoUsuario;
import com.exashare.Exashare.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query(value = """
    SELECT u.nombre_usuario AS nombreUsuario, ru.comentario, ru.puntuacion, ru.fecha
    FROM usuario u
    JOIN resenas r ON u.id_usuario = r.id_usuario
    JOIN resena_usuarios ru ON r.id_resena_usuario = ru.id_resena_usuario
    WHERE u.id_usuario = :id_usuario
    """, nativeQuery = true)
    List<Map<String, Object>> getResenasPorUsuario(@Param("id_usuario") Integer id_usuario);

    @Query(value = """
    SELECT
    u.nombre_usuario,
    h.nombre AS herramienta,
    a.fecha_inicio,
    ru.comentario
    FROM usuario u
    JOIN arriendo a ON a.id_usuario = u.id_usuario
    JOIN herramientas hr ON hr.id_arriendo = a.id_arriendo
    JOIN herramienta h ON h.id_herramienta = hr.id_herramienta
    JOIN resenas r ON r.id_usuario = u.id_usuario
    JOIN resena_usuarios ru ON ru.id_resena_usuario = r.id_resena_usuario
    WHERE u.id_usuario = :id_usuario;
    """, nativeQuery = true)
    List<Object[]> getInfoCompletaPorUsuario(@Param("id_usuario") Integer id_usuario);

    List<Usuario> findByIdTipoUsuarioAndIdMembresia(TipoUsuario idTipoUsuario, Membresia idMembresia);
}   
