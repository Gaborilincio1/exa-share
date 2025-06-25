package com.exashare.Exashare.controller;

import java.util.List;

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

import com.exashare.Exashare.model.ResenaHerramienta;
import com.exashare.Exashare.service.ResenaHerramientaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/resenasherramientas")
@Tag(name = "Reseñas de Herramientas", description = "Operaciones relacionadas con reseñas de herramientas")
public class ResenaHerramientaController {

    @Autowired
    private ResenaHerramientaService resenaHerramientaService;

    @GetMapping
    @Operation(summary = "Listar todas las reseñas de herramientas", description = "Obtiene una lista de todas las reseñas de herramientas registradas")
    public ResponseEntity<List<ResenaHerramienta>> listar() {
        List<ResenaHerramienta> resenas = resenaHerramientaService.obtenerResenas();
        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reseña de herramienta por ID", description = "Obtiene una reseña de herramienta específica por su ID")
    public ResponseEntity<ResenaHerramienta> buscar(@PathVariable Long id) {
        try {
            ResenaHerramienta resena = resenaHerramientaService.obtenerPorId(id);
            return ResponseEntity.ok(resena);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva reseña de herramienta", description = "Registra una nueva reseña de herramienta en el sistema")
    public ResponseEntity<ResenaHerramienta> guardar(@RequestBody ResenaHerramienta resena) {
        ResenaHerramienta nuevaResena = resenaHerramientaService.guardarResena(resena);
        return ResponseEntity.status(201).body(nuevaResena);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reseña de herramienta por ID", description = "Elimina una reseña de herramienta específica por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            resenaHerramientaService.eliminarResena(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reseña de herramienta por ID", description = "Actualiza una reseña de herramienta específica por su ID")
    public ResponseEntity<ResenaHerramienta> actualizar(@PathVariable Long id, @RequestBody ResenaHerramienta resena) {
        try {
            ResenaHerramienta resenaActualizada = resenaHerramientaService.updateResenaHerramienta(id, resena);
            return ResponseEntity.ok(resenaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar reseña de herramienta por ID", description = "Actualiza parcialmente una reseña de herramienta específica por su ID")
    public ResponseEntity<ResenaHerramienta> editar(@PathVariable Long id, @RequestBody ResenaHerramienta resena) {
        ResenaHerramienta resenaActualizada = resenaHerramientaService.actualizarResenaParcial(id, resena);
        if (resenaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resenaActualizada);
    }

}
