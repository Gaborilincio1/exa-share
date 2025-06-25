package com.exashare.Exashare.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.exashare.Exashare.controller.v2.TipoUsuarioControllerV2;
import com.exashare.Exashare.model.TipoUsuario;

@Component
public class TipoUsuarioModelAssembler implements RepresentationModelAssembler<TipoUsuario, EntityModel<TipoUsuario>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<TipoUsuario> toModel(TipoUsuario tipoUsuario) {
        return EntityModel.of(tipoUsuario,
                linkTo(methodOn(TipoUsuarioControllerV2.class).obtenerTipoUsuarioPorId(Long.valueOf(tipoUsuario.getIdTipoUsuario()))).withSelfRel(),
                linkTo(methodOn(TipoUsuarioControllerV2.class).obtenerTiposUsuarios()).withRel("tipos-usuarios"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).crearTipoUsuario(tipoUsuario)).withRel("crear-tipo-usuario"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).actualizarTipoUsuario(Long.valueOf(tipoUsuario.getIdTipoUsuario()), tipoUsuario)).withRel("actualizar-tipo-usuario"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).actualizarTipoUsuarioParcial(Long.valueOf(tipoUsuario.getIdTipoUsuario()), tipoUsuario)).withRel("actualizar-tipo-usuario-parcial"),
                linkTo(methodOn(TipoUsuarioControllerV2.class).eliminarTipoUsuario(Long.valueOf(tipoUsuario.getIdTipoUsuario()))).withRel("eliminar-tipo-usuario"));

    }


}
