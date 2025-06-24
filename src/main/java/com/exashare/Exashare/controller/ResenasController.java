package com.exashare.Exashare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exashare.Exashare.service.ResenasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.exashare.Exashare.model.Resenas;

import java.util.List;

@RestController
@RequestMapping("api/v1/resenasPuente")
@Tag(name = "Reseñas", description = "Operaciones relacionadas con reseñas de herramientas")
public class ResenasController {

    @Autowired
    private ResenasService resenasService;

    @GetMapping
    @Operation(summary = "Listar todas las reseñas", description = "Obtiene una lista de todas las reseñas registradas")
    public ResponseEntity<List<Resenas>> listar() {
        List<Resenas> resenas = resenasService.obtenerResenas();
        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reseña por ID", description = "Obtiene una reseña específica por su ID")
    public ResponseEntity<Resenas> buscar(@PathVariable Long id) {
        try {
            Resenas resena = resenasService.obtenerPorId(id);
            return ResponseEntity.ok(resena);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva reseña", description = "Registra una nueva reseña en el sistema")
    public ResponseEntity<Resenas> guardar(@RequestBody Resenas resenas) {
        Resenas nuevaResena = resenasService.guardarResenas(resenas);
        return ResponseEntity.status(201).body(nuevaResena);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reseña por ID", description = "Elimina una reseña específica por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            resenasService.eliminarResenas(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reseña por ID", description = "Actualiza una reseña específica por su ID")
    public ResponseEntity<Resenas> actualizar(@PathVariable Long id, @RequestBody Resenas resenas) {
        try {
            Resenas resenaActualizada = resenasService.updateResenas(id, resenas);
            return ResponseEntity.ok(resenaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar reseña parcialmente por ID", description = "Actualiza parcialmente una reseña específica por su ID")
    public ResponseEntity<Resenas> actualizarParcial(@PathVariable Long id, @RequestBody Resenas resenas) {
        Resenas actualizado = resenasService.actualizarResenasParcial(id, resenas);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

}
