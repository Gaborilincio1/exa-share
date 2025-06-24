package com.exashare.Exashare.controller.v2;

import com.exashare.Exashare.model.TipoUsuario;
import com.exashare.Exashare.model.Usuario;
import com.exashare.Exashare.service.TipoUsuarioService;
import com.exashare.Exashare.assemblers.TipoUsuarioModelAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v2/tipos-usuarios")
public class TipoUsuarioControllerV2 {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @Autowired
    private TipoUsuarioModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<TipoUsuario>> obtenerTiposUsuarios() {
        List<EntityModel<TipoUsuario>> tipos = tipoUsuarioService.obtenerTiposUsuario()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tipos);
    }

    @GetMapping("/{id}")
    public EntityModel<TipoUsuario> obtenerTipoUsuarioPorId(@PathVariable Long id) {
        TipoUsuario tipo = tipoUsuarioService.obtenerPorId(id);
        return assembler.toModel(tipo);
    }

    @PostMapping
    public EntityModel<TipoUsuario> crearTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario nuevo = tipoUsuarioService.guardarTipoUsuario(tipoUsuario);
        return assembler.toModel(nuevo);
    }

    @PutMapping("/{id}")
    public EntityModel<TipoUsuario> actualizarTipoUsuario(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario actualizado = tipoUsuarioService.actualizarTipoUsuario(id, tipoUsuario);
        return assembler.toModel(actualizado);
    }

    @PatchMapping("/{id}")
    public EntityModel<TipoUsuario> actualizarTipoUsuarioParcial(@PathVariable Long id, @RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario actualizado = tipoUsuarioService.actualizarTipoUsuarioParcial(id, tipoUsuario);
        return assembler.toModel(actualizado);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminarTipoUsuario(@PathVariable Long id) {
        TipoUsuario existing = tipoUsuarioService.obtenerPorId(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        tipoUsuarioService.eliminarTipoUsuario(id);
        return ResponseEntity.noContent().build();
    }
}