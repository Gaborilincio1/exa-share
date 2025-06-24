package com.exashare.Exashare.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.exashare.Exashare.controller.v2.ResenaUsuarioControllerV2;
import com.exashare.Exashare.model.ResenaUsuario;

@Component
public class ResenaUsuarioModelAssembler implements RepresentationModelAssembler<ResenaUsuario, EntityModel<ResenaUsuario>> {
    @SuppressWarnings("null")
    @Override
    public EntityModel<ResenaUsuario> toModel(ResenaUsuario resenaUsuario) {
        return EntityModel.of(resenaUsuario,
                linkTo(methodOn(ResenaUsuarioControllerV2.class).obtenerResenaUsuarioPorId(Long.valueOf(resenaUsuario.getIdResenaUsuario()))).withSelfRel(),
                linkTo(methodOn(ResenaUsuarioControllerV2.class).obtenerResenasUsuarios()).withRel("resenas-usuarios"),
                linkTo(methodOn(ResenaUsuarioControllerV2.class).crearResenaUsuario(resenaUsuario)).withRel("crear-resena-usuario"),
                linkTo(methodOn(ResenaUsuarioControllerV2.class).actualizarResenaUsuario(Long.valueOf(resenaUsuario.getIdResenaUsuario()), resenaUsuario)).withRel("actualizar-resena-usuario"),
                linkTo(methodOn(ResenaUsuarioControllerV2.class).actualizarResenaUsuarioParcial(Long.valueOf(resenaUsuario.getIdResenaUsuario()), resenaUsuario)).withRel("actualizar-resena-usuario-parcial"),
                linkTo(methodOn(ResenaUsuarioControllerV2.class).eliminarResenaUsuario(Long.valueOf(resenaUsuario.getIdResenaUsuario()))).withRel("eliminar-resena-usuario"));
    }
}
