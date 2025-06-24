package com.exashare.Exashare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exashare.Exashare.model.ResenaUsuario;
import com.exashare.Exashare.service.ResenaUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("api/v1/resenasusuarios")
@Tag(name = "Reseñas de Usuarios", description = "Operaciones relacionadas con las reseñas de usuarios")
public class ResenaUsuarioController {

    @Autowired
    private ResenaUsuarioService resenaUsuarioService;

    @GetMapping
    @Operation(summary = "Listar todas las reseñas de usuarios", description = "Obtiene una lista de todas las reseñas de usuarios registradas")
    public ResponseEntity<List<ResenaUsuario>> listar() {
        List<ResenaUsuario> resenas = resenaUsuarioService.obtenerResenas();
        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reseña de usuario por ID", description = "Obtiene una reseña de usuario específica por su ID")
    public ResponseEntity<ResenaUsuario> buscar(@PathVariable Long id) {
        try {
            ResenaUsuario resena = resenaUsuarioService.obtenerPorId(id);
            return ResponseEntity.ok(resena);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva reseña de usuario", description = "Registra una nueva reseña de usuario en el sistema")
    public ResponseEntity<ResenaUsuario> guardar(@RequestBody ResenaUsuario resena) {
        ResenaUsuario nuevaResena = resenaUsuarioService.guardarResena(resena);
        return ResponseEntity.status(201).body(nuevaResena);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reseña de usuario por ID", description = "Elimina una reseña de usuario específica por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            resenaUsuarioService.eliminarResena(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reseña de usuario por ID", description = "Actualiza una reseña de usuario específica por su ID")
    public ResponseEntity<ResenaUsuario> actualizar(@PathVariable Long id, @RequestBody ResenaUsuario resena) {
        try {
            ResenaUsuario resenaActualizada = resenaUsuarioService.actualizarResena(id, resena);
            return ResponseEntity.ok(resenaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar reseña de usuario parcialmente por ID", description = "Actualiza parcialmente una reseña de usuario específica por su ID")
    public ResponseEntity<ResenaUsuario> editar(@PathVariable Long id, @RequestBody ResenaUsuario resena) {
        ResenaUsuario resenaActualizada = resenaUsuarioService.actualizarResenaParcial(id, resena);
        if (resenaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resenaActualizada);
    }

}
