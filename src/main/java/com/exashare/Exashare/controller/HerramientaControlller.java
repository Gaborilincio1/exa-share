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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.repository.HerramientaRepository;
import com.exashare.Exashare.service.HerramientaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/herramientas")
@Tag(name = "Herramientas", description = "Operaciones relacionadas con herramientas")
public class HerramientaControlller {

    @Autowired
    private HerramientaRepository herramientaRepository;
    @Autowired
    private HerramientaService HerramientaService;

    @GetMapping
    @Operation(summary = "Listar todas las herramientas", description = "Obtiene una lista de todas las herramientas registradas")
    public ResponseEntity<List<Herramienta>> listar() {
        List<Herramienta> Herramientas = HerramientaService.obtenerHerramientas();
        if (Herramientas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Herramientas);
    }

    @GetMapping("/por-categoria")
    public List<Object[]> obtenerHerramientasPorCategoria(@RequestParam String categoria) {
        return HerramientaService.obtenerHerramientasPorCategoria(categoria);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar herramienta por ID", description = "Obtiene una herramienta específica por su ID")
    public ResponseEntity<Herramienta> buscar(@PathVariable Long id) {
        try {
            Herramienta Herramienta = HerramientaService.obtenerPorId(id);
            return ResponseEntity.ok(Herramienta);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva herramienta", description = "Registra una nueva herramienta en el sistema")
    public ResponseEntity<Herramienta> guardar(@RequestBody Herramienta Herramienta) {
        Herramienta nuevoHerramienta = HerramientaService.guardarHerramienta(Herramienta);
        return ResponseEntity.status(201).body(nuevoHerramienta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar herramienta por ID", description = "Elimina una herramienta específica por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            HerramientaService.eliminarHerramienta(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar herramienta por ID", description = "Actualiza los detalles de una herramienta específica por su ID")   
    public ResponseEntity<Herramienta> actualizar(@PathVariable Long id, @RequestBody Herramienta Herramienta) {
        try {
            Herramienta HerramientaoActualizado = HerramientaService.updateHerramienta(id, Herramienta);
            return ResponseEntity.ok(HerramientaoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente herramienta por ID", description = "Actualiza parcialmente los detalles de una herramienta específica por su ID")
    public ResponseEntity<Herramienta> editar(@PathVariable Long id, @RequestBody Herramienta Herramienta) {
        Herramienta HerramientaActualizada = HerramientaService.actualizarHerramientaParcial(id, Herramienta);
        if (HerramientaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(HerramientaActualizada);
    }

}
