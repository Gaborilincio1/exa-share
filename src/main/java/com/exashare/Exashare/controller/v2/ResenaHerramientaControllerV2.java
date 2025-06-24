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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exashare.Exashare.assemblers.ResenaHerramientaModelAssembler;
import com.exashare.Exashare.model.ResenaHerramienta;
import com.exashare.Exashare.service.ResenaHerramientaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("/api/v2/resenaHerramientas")
public class ResenaHerramientaControllerV2 {

    @Autowired
    private ResenaHerramientaService resenaHerramientaService;

    @Autowired
    private ResenaHerramientaModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<ResenaHerramienta>> obtenerResenas() {
        List<EntityModel<ResenaHerramienta>> resenas = resenaHerramientaService.obtenerResenas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(resenas,
            linkTo(methodOn(ResenaHerramientaControllerV2.class).obtenerResenas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResenaHerramienta>> obtenerPorId(@PathVariable Long id) {
        ResenaHerramienta resenaHerramientas = resenaHerramientaService.obtenerPorId(id);
        if (resenaHerramientas == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(resenaHerramientas));
    }



    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResenaHerramienta>> guardarResena(@RequestBody ResenaHerramienta resenaHerramienta) {
        ResenaHerramienta savedResena = resenaHerramientaService.guardarResena(resenaHerramienta);
        return ResponseEntity
                .created(linkTo(methodOn(ResenaHerramientaControllerV2.class).obtenerPorId(Long.valueOf(savedResena.getIdResenaHerramienta()))).toUri())
                .body(assembler.toModel(resenaHerramienta));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResenaHerramienta>> actualizarResena(@PathVariable Long id, @RequestBody ResenaHerramienta resenaHerramienta) {
        resenaHerramienta.setIdResenaHerramienta(id.intValue());
        ResenaHerramienta updated = resenaHerramientaService.guardarResena(resenaHerramienta);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResenaHerramienta>> actualizarResenaParcial(@PathVariable Long id, @RequestBody ResenaHerramienta resenaHerramienta) {
        ResenaHerramienta patched = resenaHerramientaService.actualizarResenaParcial(id, resenaHerramienta);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        ResenaHerramienta resenaHerramienta = resenaHerramientaService.obtenerPorId(id);
        if (resenaHerramienta == null) {
            return ResponseEntity.notFound().build();
        }
        resenaHerramientaService.eliminarResena(id);
        return ResponseEntity.noContent().build();
    }

}
