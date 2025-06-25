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

import com.exashare.Exashare.assemblers.ResenaUsuarioModelAssembler;
import com.exashare.Exashare.model.ResenaUsuario;
import com.exashare.Exashare.service.ResenaUsuarioService;

public class ResenaUsuarioControllerV2 {

    @Autowired
    private ResenaUsuarioService resenaUsuarioService;

    @Autowired
    private ResenaUsuarioModelAssembler assembler;

   @GetMapping
    public CollectionModel<EntityModel<ResenaUsuario>> obtenerResenasUsuarios() {
        List<EntityModel<ResenaUsuario>> resenas = resenaUsuarioService.obtenerResenas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(resenas,
            linkTo(methodOn(ResenaUsuarioControllerV2.class).obtenerResenasUsuarios()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResenaUsuario>> obtenerResenaUsuarioPorId(@PathVariable Long id) {
        ResenaUsuario resenas = resenaUsuarioService.obtenerPorId(id);
        if (resenas == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(resenas));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResenaUsuario>> crearResenaUsuario(@RequestBody ResenaUsuario resena) {
        ResenaUsuario nuevaResena = resenaUsuarioService.guardarResena(resena);
        return ResponseEntity
                .created(linkTo(methodOn(ResenaUsuarioControllerV2.class).obtenerResenaUsuarioPorId(Long.valueOf(nuevaResena.getIdResenaUsuario()))).toUri())
                .body(assembler.toModel(nuevaResena));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResenaUsuario>> actualizarResenaUsuario(@PathVariable Long id, @RequestBody ResenaUsuario resena) {
        resena.setIdResenaUsuario(id.intValue());
        ResenaUsuario updated = resenaUsuarioService.guardarResena(resena);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResenaUsuario>> actualizarResenaUsuarioParcial(@PathVariable Long id, @RequestBody ResenaUsuario resena) {
        ResenaUsuario patched = resenaUsuarioService.actualizarResena(id, resena);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminarResenaUsuario(@PathVariable Long id) {
        ResenaUsuario existing = resenaUsuarioService.obtenerPorId(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        resenaUsuarioService.eliminarResena(id);
        return ResponseEntity.noContent().build();
    }

}
