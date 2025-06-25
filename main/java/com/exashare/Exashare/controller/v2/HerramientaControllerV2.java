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

import com.exashare.Exashare.assemblers.HerramientaModelAssembler;
import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.service.HerramientaService;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v2/herramientas")
@Tag(name = "HerramientasV2", description = "Operaciones relacionadas con herramientas - Versión 2")
public class HerramientaControllerV2 {

    @Autowired
    private HerramientaService herramientaService;

    @Autowired
    private HerramientaModelAssembler assembler;

   @GetMapping
    public CollectionModel<EntityModel<Herramienta>> obtenerHerramientas() {
        List<EntityModel<Herramienta>> herramientas = herramientaService.obtenerHerramientas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(herramientas,
            linkTo(methodOn(HerramientaControllerV2.class).obtenerHerramientas()).withSelfRel());
    }

    

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Herramienta>> obtenerHerramientaPorId(@PathVariable Long id) {
        Herramienta herramienta = herramientaService.obtenerPorId(id);
        if (herramienta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(herramienta));
    }

    @GetMapping(value = "/rangoPrecios/{precioMinimo}/{precioMaximo}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Herramienta>> getHerramientasByPrecioBetween(
            @PathVariable Integer precioMinimo, @PathVariable Integer precioMaximo) {
        List<EntityModel<Herramienta>> herramientas = herramientaService.buscarPorRangoPrecio(precioMinimo, precioMaximo).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                herramientas,
                linkTo(methodOn(HerramientaControllerV2.class).getHerramientasByPrecioBetween(precioMinimo, precioMaximo)).withSelfRel()
        );
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Herramienta>> crearHerramienta(@RequestBody Herramienta herramienta) {
        Herramienta nuevaHerramienta = herramientaService.guardarHerramienta(herramienta);
        return ResponseEntity
                .created(linkTo(methodOn(HerramientaControllerV2.class).obtenerHerramientaPorId(Long.valueOf(nuevaHerramienta.getIdHerramienta()))).toUri())
                .body(assembler.toModel(nuevaHerramienta));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Herramienta>> actualizarHerramienta(@PathVariable Long id, @RequestBody Herramienta herramienta) {
        herramienta.setIdHerramienta(id.intValue());
        Herramienta updated = herramientaService.guardarHerramienta(herramienta);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Herramienta>> actualizarHerramientaParcial(@PathVariable Long id, @RequestBody Herramienta herramienta) {
        Herramienta patched = herramientaService.actualizarHerramientaParcial(id, herramienta);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminarHerramienta(@PathVariable Long id) {
        Herramienta existing = herramientaService.obtenerPorId(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        herramientaService.eliminarHerramienta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/info-herramientas/{estado}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Object> obtenerHerramientasYUsuarioPorEstado(@PathVariable String estado) {
    List<Object[]> herramientas = herramientaService
            .obtenerHerramientasYUsuarioPorEstado(estado)
            .stream()
            .toList(); 

    return ResponseEntity.ok(herramientas);
}
    


}


    

