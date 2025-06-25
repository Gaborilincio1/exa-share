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

import com.exashare.Exashare.service.HerramientasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.exashare.Exashare.model.Herramientas;

import java.util.List;

@RestController
@RequestMapping("api/v1/herramientasPuente")
@Tag(name = "HerramientasPuente", description = "Operaciones relacionadas con herramientas")
public class HerramientasController {
    
    @Autowired
    private HerramientasService herramientasService;

    @GetMapping
    @Operation(summary = "Listar todas las herramientas", description = "Obtiene una lista de todas las herramientas registradas")
    public ResponseEntity<List<Herramientas>> listar() {
        List<Herramientas> herramientas = herramientasService.obtenerHerramientas();
        if (herramientas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(herramientas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar herramienta por ID", description = "Obtiene una herramienta específica por su ID")
    public ResponseEntity<Herramientas> buscar(@PathVariable Long id) {
        try {
            Herramientas herramienta = herramientasService.obtenerPorId(id);
            return ResponseEntity.ok(herramienta);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva herramienta", description = "Registra una nueva herramienta en el sistema")
    public ResponseEntity<Herramientas> guardar(@RequestBody Herramientas herramientas) {
        Herramientas nuevaHerramienta = herramientasService.guardarHerramientas(herramientas);
        return ResponseEntity.status(201).body(nuevaHerramienta);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar herramienta por ID", description = "Elimina una herramienta específica por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            herramientasService.eliminarHerramientas(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar herramienta por ID", description = "Actualiza una herramienta específica por su ID")
    public ResponseEntity<Herramientas> actualizar(@PathVariable Long id, @RequestBody Herramientas herramientas) {
        try {
            Herramientas herramientaActualizada = herramientasService.updateHerramientas(id, herramientas);
            return ResponseEntity.ok(herramientaActualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente herramienta por ID", description = "Actualiza parcialmente una herramienta específica por su ID")
    public ResponseEntity<Herramientas> actualizarParcial(@PathVariable Long id, @RequestBody Herramientas herramientas) {
        Herramientas actualizado = herramientasService.actualizarHerramientasParcial(id, herramientas);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }
}