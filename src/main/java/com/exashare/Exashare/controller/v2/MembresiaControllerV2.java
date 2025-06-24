package com.exashare.Exashare.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.exashare.Exashare.assemblers.MembresiaModelAssembler;
import com.exashare.Exashare.model.Membresia;
import com.exashare.Exashare.service.MembresiaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public class MembresiaControllerV2 {

    @Autowired
    private MembresiaService membresiaService;

    @Autowired
    private MembresiaModelAssembler assembler;

   @GetMapping
    public CollectionModel<EntityModel<Membresia>> obtenerMembresias() {
        List<EntityModel<Membresia>> membresias = membresiaService.obtenerMembresias().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(membresias,
            linkTo(methodOn(MembresiaControllerV2.class).obtenerMembresias()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Membresia>> obtenerMembresiaPorId(@PathVariable Long id) {
        Membresia membresia = membresiaService.obtenerPorId(id);
        if (membresia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(membresia));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Membresia>> crearMembresia(@RequestBody Membresia membresia) {
        Membresia nuevaMembresia = membresiaService.guardarMembresia(membresia);
        return ResponseEntity
                .created(linkTo(methodOn(MembresiaControllerV2.class).obtenerMembresiaPorId(Long.valueOf(nuevaMembresia.getIdMembresia()))).toUri())
                .body(assembler.toModel(nuevaMembresia));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Membresia>> actualizarMembresia(@PathVariable Long id, @RequestBody Membresia membresia) {
        membresia.setIdMembresia(id.intValue());
        Membresia updated = membresiaService.guardarMembresia(membresia);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Membresia>> actualizarMembresiaParcial(@PathVariable Long id, @RequestBody Membresia membresia) {
        Membresia patched = membresiaService.actualizarMembresiaParcial(id, membresia);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminarMembresia(@PathVariable Long id) {
        Membresia existing = membresiaService.obtenerPorId(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        membresiaService.eliminarMembresia(id);
        return ResponseEntity.noContent().build();
    }
}
