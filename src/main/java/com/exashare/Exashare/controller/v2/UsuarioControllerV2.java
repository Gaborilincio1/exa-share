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

import com.exashare.Exashare.assemblers.UsuarioModelAssembler;
import com.exashare.Exashare.model.Membresia;
import com.exashare.Exashare.model.TipoUsuario;
import com.exashare.Exashare.model.Usuario;
import com.exashare.Exashare.service.UsuarioService;

@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

   @GetMapping
    public CollectionModel<EntityModel<Usuario>> obtenerUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.obtenerUsuarios().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
            linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarios()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario Usuario = usuarioService.obtenerPorId(id);
        if (Usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(Usuario));
    }

    @GetMapping(value = "/tipousuario-membresia/{idTipoUsuario}/{idMembresia}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Usuario>> obtenerUsuarioPorTipoUsuarioYMembresia(@PathVariable TipoUsuario idTipoUsuario, @PathVariable Membresia idMembresia) {
        List<EntityModel<Usuario>> usuarios = usuarioService.findByIdTipoUsuarioAndIdMembresia(idTipoUsuario, idMembresia).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarioPorTipoUsuarioYMembresia(idTipoUsuario, idMembresia)).withSelfRel()
        );
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarioPorId(Long.valueOf(nuevoUsuario.getIdUsuario()))).toUri())
                .body(assembler.toModel(nuevoUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario updated = usuarioService.guardarUsuario(usuario);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> actualizarUsuarioParcial(@PathVariable Long id, @RequestBody Usuario Usuario) {
        Usuario patched = usuarioService.actualizarUsuarioParcial(id, Usuario);
        if (patched == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        Usuario existing = usuarioService.obtenerPorId(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{info-completa}", produces = MediaTypes.HAL_JSON_VALUE)
    public List<Object[]> getInfoCompletaPorUsuario(@PathVariable("info-completa") Integer idUsuario) {
        return usuarioService.getInfoCompletaPorUsuario(idUsuario);
    }

}
