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

import com.exashare.Exashare.model.Arriendo;
import com.exashare.Exashare.service.ArriendoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/arriendos")
@Tag(name = "Arriendos", description = "Operaciones relacionadas con arriendos de herramientas")
public class ArriendoController {

    @Autowired
    private ArriendoService arriendoService;

    @GetMapping
    @Operation(summary = "Listar todos los arriendos", description = "Obtiene una lista de todos los arriendos registrados")
    public ResponseEntity<List<Arriendo>> listar() {
        List<Arriendo> arriendos = arriendoService.obtenerArriendos();
        if (arriendos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(arriendos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar arriendo por ID", description = "Obtiene un arriendo específico por su ID")
    public ResponseEntity<Arriendo> buscar(@PathVariable Long id) {
        try {
            Arriendo arriendo = arriendoService.obtenerPorId(id);
            return ResponseEntity.ok(arriendo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    @Operation(summary = "Guardar un nuevo arriendo", description = "Crea un nuevo arriendo y lo guarda en la base de datos")
    public ResponseEntity<Arriendo> guardar(@RequestBody Arriendo arriendo) {
        Arriendo nuevoArriendo = arriendoService.guardarArriendo(arriendo);
        return ResponseEntity.status(201).body(nuevoArriendo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un arriendo", description = "Elimina un arriendo específico por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            arriendoService.eliminarArriendo(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un arriendo", description = "Actualiza un arriendo existente por su ID")
    public ResponseEntity<Arriendo> actualizar(@PathVariable Long id, @RequestBody Arriendo arriendo) {
        try {
            Arriendo arriendooActualizado = arriendoService.updateArriendo(id, arriendo);
            return ResponseEntity.ok(arriendooActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Editar un arriendo", description = "Edita parcialmente un arriendo existente por su ID")
    public ResponseEntity<Arriendo> editar(@PathVariable Long id, @RequestBody Arriendo arriendo) {
        Arriendo arriendoActualizada = arriendoService.actualizarArriendoParcial(id, arriendo);
        if (arriendoActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(arriendoActualizada);
    }
}
