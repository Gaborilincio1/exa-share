package com.exashare.Exashare.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.exashare.Exashare.model.Resenas;
import com.exashare.Exashare.controller.v2.ResenasControllerV2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ResenasModelAssembler implements RepresentationModelAssembler<Resenas, EntityModel<Resenas>> {
    @SuppressWarnings("null")
    @Override
    public EntityModel<Resenas> toModel(Resenas resenas) {
        return EntityModel.of(resenas,
                linkTo(methodOn(ResenasControllerV2.class).obtenerResenasPorId(Long.valueOf(resenas.getIdResenas()))).withSelfRel(),
                linkTo(methodOn(ResenasControllerV2.class).obtenerResenas()).withRel("resenas"),
                linkTo(methodOn(ResenasControllerV2.class).crearResena(resenas)).withRel("crear-resena"),
                linkTo(methodOn(ResenasControllerV2.class).actualizarResena(Long.valueOf(resenas.getIdResenas()), resenas)).withRel("actualizar-resena"),
                linkTo(methodOn(ResenasControllerV2.class).actualizarResenasParcial(Long.valueOf(resenas.getIdResenas()), resenas)).withRel("actualizar-resena-parcial"),
                linkTo(methodOn(ResenasControllerV2.class).eliminarResenas(Long.valueOf(resenas.getIdResenas()))).withRel("eliminar-resena"));
    }
}