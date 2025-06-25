package com.exashare.Exashare.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.exashare.Exashare.controller.v2.MembresiaControllerV2;
import com.exashare.Exashare.model.Membresia;
@Component

public class MembresiaModelAssembler implements RepresentationModelAssembler<Membresia, EntityModel<Membresia>> {
    @SuppressWarnings ("null")
    @Override
    public EntityModel<Membresia> toModel(Membresia membresia) {
        return EntityModel.of(membresia,
                linkTo(methodOn(MembresiaControllerV2.class).obtenerMembresiaPorId(Long.valueOf(membresia.getIdMembresia()))).withSelfRel(),
                linkTo(methodOn(MembresiaControllerV2.class).obtenerMembresias()).withRel("membresias"),
                linkTo(methodOn(MembresiaControllerV2.class).crearMembresia(membresia)).withRel("crear-membresia"),
                linkTo(methodOn(MembresiaControllerV2.class).actualizarMembresia(Long.valueOf(membresia.getIdMembresia()), membresia)).withRel("actualizar-membresia"),
                linkTo(methodOn(MembresiaControllerV2.class).actualizarMembresiaParcial(Long.valueOf(membresia.getIdMembresia()), membresia)).withRel("actualizar-membresia-parcial"),
                linkTo(methodOn(MembresiaControllerV2.class).eliminarMembresia(Long.valueOf(membresia.getIdMembresia()))).withRel("eliminar-herramienta"));
    }

}
