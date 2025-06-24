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
    SELECT u.nombre_usuario, h.nombre AS herramienta, a.fecha_inicio, r.comentario
    FROM usuario u
    JOIN herramienta h ON u.id_usuario = h.id_usuario
    JOIN arriendo a ON h.id_herramienta = a.id_herramienta
    JOIN resenas r ON a.id_arriendo = r.id_arriendo
    WHERE u.id_usuario = :id_usuario
    """, nativeQuery = true)
    List<Object[]> getInfoCompletaPorUsuario(@Param("id_usuario") Integer id_usuario);

    List<Usuario> findByIdTipoUsuarioAndIdMembresia(TipoUsuario idTipoUsuario, Membresia idMembresia);

    List<Usuario> findByMembresia(Membresia membresia);

    List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);
}   
