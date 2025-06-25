package com.exashare.Exashare.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.exashare.Exashare.controller.v2.UsuarioControllerV2;
import com.exashare.Exashare.model.Usuario;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarioPorId(Long.valueOf(usuario.getIdUsuario()))).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuarioControllerV2.class).crearUsuario(usuario)).withRel("crear-usuario"),
                linkTo(methodOn(UsuarioControllerV2.class).obtenerUsuarioPorTipoUsuarioYMembresia(usuario.getIdTipoUsuario(), usuario.getIdMembresia())).withRel("usuarios-filtrados-por-tipo-y-membresia"),
                linkTo(methodOn(UsuarioControllerV2.class).actualizarUsuario(Long.valueOf(usuario.getIdUsuario()), usuario)).withRel("actualizar-usuario"),
                linkTo(methodOn(UsuarioControllerV2.class).actualizarUsuarioParcial(Long.valueOf(usuario.getIdUsuario()),usuario)).withRel("actualizar-usuario-parcial"),
                linkTo(methodOn(UsuarioControllerV2.class).eliminarUsuario(Long.valueOf(usuario.getIdUsuario()))).withRel("eliminar-usuario"),
                linkTo(methodOn(UsuarioControllerV2.class).getResenasPorUsuario(usuario.getIdUsuario())).withRel("resenas-por-usuario"));
                
    }
}