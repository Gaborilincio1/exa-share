package com.exashare.Exashare.controller.v2;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exashare.Exashare.assemblers.ArriendoModelAssembler;
import com.exashare.Exashare.model.Arriendo;
import com.exashare.Exashare.service.ArriendoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/arriendos")
@Tag(name = "ArriendosV2", description = "Operaciones relacionadas con arriendos de herramientas - Versión 2")
public class ArriendoControllerV2 {

    @Autowired
    private ArriendoService arriendoService;

    @Autowired
    private ArriendoModelAssembler assembler;

    @Operation(summary = "Listar todos los arriendos", description = "Obtiene todos los registros de arriendos disponibles en el sistema")
    @GetMapping
    public CollectionModel<EntityModel<Arriendo>> obtenerArriendos() {
        List<EntityModel<Arriendo>> arriendos = arriendoService.obtenerArriendos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(arriendos,
            linkTo(methodOn(ArriendoControllerV2.class).obtenerArriendos()).withSelfRel());
    }

    @Operation(summary = "Buscar arriendo por ID", description = "Obtiene un arriendo específico según su ID")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Arriendo>> obtenerArriendoPorId(@PathVariable Long id) {
        Arriendo arriendo = arriendoService.obtenerPorId(id);
        if (arriendo == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(arriendo));
    }

    @Operation(summary = "Buscar arriendos por rango de fechas", description = "Filtra arriendos según la fecha de inicio dentro del rango proporcionado")
    @GetMapping(value = "/fechas/{fechaInicio}/{fechaFin}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Arriendo>> getArriendosByFechaInicioBetween(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {

        List<EntityModel<Arriendo>> arriendos = arriendoService.findByFechaInicioBetween(fechaInicio, fechaFin).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(arriendos,
            linkTo(methodOn(ArriendoControllerV2.class).getArriendosByFechaInicioBetween(fechaInicio, fechaFin)).withSelfRel());
    }

    @Operation(summary = "Buscar arriendos por estado y fechas", description = "Obtiene arriendos con un estado específico dentro de un rango de fechas")
    @GetMapping(value = "/estado/{estado}/fechas/{fechaInicio}/{fechaFin}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Arriendo>> getArriendosByEstadoAndFechaInicioBetween(
            @PathVariable String estado,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {

        List<EntityModel<Arriendo>> arriendos = arriendoService.findByEstadoAndFechaInicioBetween(estado, fechaInicio, fechaFin).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(arriendos,
            linkTo(methodOn(ArriendoControllerV2.class).getArriendosByEstadoAndFechaInicioBetween(estado, fechaInicio, fechaFin)).withSelfRel());
    }

    @Operation(summary = "Crear un nuevo arriendo", description = "Registra un nuevo arriendo en el sistema")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Arriendo>> crearArriendo(@RequestBody Arriendo arriendo) {
        Arriendo nuevoArriendo = arriendoService.guardarArriendo(arriendo);
        return ResponseEntity
            .created(linkTo(methodOn(ArriendoControllerV2.class).obtenerArriendoPorId((long) nuevoArriendo.getIdArriendo())).toUri())
            .body(assembler.toModel(nuevoArriendo));
    }

    @Operation(summary = "Actualizar un arriendo existente", description = "Reemplaza completamente los datos de un arriendo usando su ID")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Arriendo>> actualizarArriendo(@PathVariable Long id, @RequestBody Arriendo arriendo) {
        arriendo.setIdArriendo(id.intValue());
        Arriendo updated = arriendoService.guardarArriendo(arriendo);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Actualizar parcialmente un arriendo", description = "Modifica algunos campos de un arriendo específico")
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Arriendo>> actualizarArriendoParcial(@PathVariable Long id, @RequestBody Arriendo arriendo) {
        Arriendo patched = arriendoService.actualizarArriendoParcial(id, arriendo);
        if (patched == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @Operation(summary = "Eliminar un arriendo", description = "Elimina un arriendo del sistema por su ID")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminarArriendo(@PathVariable Long id) {
        Arriendo existing = arriendoService.obtenerPorId(id);
        if (existing == null) return ResponseEntity.notFound().build();
        arriendoService.eliminarArriendo(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener arriendos con herramientas y usuarios", description = "Consulta avanzada que muestra detalles de arriendos con herramientas y usuarios asociados")
    @GetMapping(value = "/herramienta-usuario", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Map<String, Object>>> getArriendosConUsuarioYHerramienta() {
        List<Map<String, Object>> resultados = arriendoService.getArriendoConUsuarioYHerramienta();
        List<EntityModel<Map<String, Object>>> models = resultados.stream()
            .map(result -> EntityModel.of(result,
                linkTo(methodOn(ArriendoControllerV2.class).getArriendosConUsuarioYHerramienta()).withSelfRel()))
            .collect(Collectors.toList());

        return CollectionModel.of(models,
            linkTo(methodOn(ArriendoControllerV2.class).getArriendosConUsuarioYHerramienta()).withSelfRel());
    }
}
