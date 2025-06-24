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
import org.springframework.web.bind.annotation.RequestBody;

import com.exashare.Exashare.assemblers.ResenasModelAssembler;
import com.exashare.Exashare.model.Resenas;
import com.exashare.Exashare.service.ResenasService;

@RestController
@RequestMapping ("/api/v2/resenas")    
public class ResenasControllerV2 {
    @Autowired
    private ResenasService resenasService;

    @Autowired
    private ResenasModelAssembler assembler;

   @GetMapping
    public CollectionModel<EntityModel<Resenas>> obtenerResenas() {
        List<EntityModel<Resenas>> resenas = resenasService.obtenerResenas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(resenas,
            linkTo(methodOn(ResenasControllerV2.class).obtenerResenas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Resenas>> obtenerResenasPorId(@PathVariable Long id) {
        Resenas resena = resenasService.obtenerPorId(id);
        if (resena == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(resena));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Resenas>> crearResena(@RequestBody Resenas resena) {
        Resenas nuevaResena = resenasService.guardarResenas(resena);
        return ResponseEntity
                .created(linkTo(methodOn(ResenasControllerV2.class).obtenerResenasPorId(Long.valueOf(nuevaResena.getIdResenas()))).toUri())
                .body(assembler.toModel(nuevaResena));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Resenas>> actualizarResena(@PathVariable Long id, @RequestBody Resenas resena) {
        resena.setIdResenas(id.intValue());
        Resenas updated = resenasService.guardarResenas(resena);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }
    
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Resenas>> actualizarResenasParcial(@PathVariable Long id, @RequestBody Resenas resenas) {
        Resenas patched = resenasService.actualizarResenasParcial(id, resenas);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminarResenas(@PathVariable Long id) {
        Resenas existing = resenasService.obtenerPorId(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        resenasService.eliminarResenas(id);
        return ResponseEntity.noContent().build();
    }

}
