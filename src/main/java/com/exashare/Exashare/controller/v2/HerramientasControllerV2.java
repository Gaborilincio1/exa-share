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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exashare.Exashare.assemblers.HerramientasModelAssembler;
import com.exashare.Exashare.model.Herramientas;
import com.exashare.Exashare.service.HerramientasService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v2/herramientasPuente")
@Tag(name = "HerramientasPuente", description = "Operaciones relacionadas con herramientas")
public class HerramientasControllerV2 {

    @Autowired
    private HerramientasService herramientasService;

    @Autowired
    private HerramientasModelAssembler assembler;

   @GetMapping
    public CollectionModel<EntityModel<Herramientas>> obtenerHerramientas() {
        List<EntityModel<Herramientas>> herramientas = herramientasService.obtenerHerramientas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(herramientas,
            linkTo(methodOn(HerramientasControllerV2.class).obtenerHerramientas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Herramientas>> obtenerHerramientasPorId(@PathVariable Long id) {
        Herramientas herramientas = herramientasService.obtenerPorId(id);
        if (herramientas == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(herramientas));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Herramientas>> guardarHerramientas(@RequestBody Herramientas herramientas) {
        Herramientas nuevaHerramientas = herramientasService.guardarHerramientas(herramientas);
        return ResponseEntity
                .created(linkTo(methodOn(HerramientasControllerV2.class).obtenerHerramientasPorId(Long.valueOf(nuevaHerramientas.getIdHerramientas()))).toUri())
                .body(assembler.toModel(nuevaHerramientas));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Herramientas>> updateHerramientas(@PathVariable Long id, @RequestBody Herramientas herramientas) {
        herramientas.setIdHerramientas(id.intValue());
        Herramientas updated = herramientasService.guardarHerramientas(herramientas);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Herramientas>> actualizarHerramientasParcial(@PathVariable Long id, @RequestBody Herramientas herramientas) {
        Herramientas patched = herramientasService.actualizarHerramientasParcial(id, herramientas);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminarHerramientas(@PathVariable Long id) {
        Herramientas existing = herramientasService.obtenerPorId(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        herramientasService.eliminarHerramientas(id);
        return ResponseEntity.noContent().build();
    }
}
