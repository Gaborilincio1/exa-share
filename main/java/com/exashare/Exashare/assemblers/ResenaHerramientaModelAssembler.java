package com.exashare.Exashare.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.exashare.Exashare.controller.v2.ResenaHerramientaControllerV2;
import com.exashare.Exashare.model.ResenaHerramienta;

@Component
public class ResenaHerramientaModelAssembler implements RepresentationModelAssembler<ResenaHerramienta, EntityModel<ResenaHerramienta>> 
{
    @SuppressWarnings("null")
    @Override
    public EntityModel<ResenaHerramienta> toModel(ResenaHerramienta resenaHerramienta) {
        return EntityModel.of(resenaHerramienta,
                linkTo(methodOn(ResenaHerramientaControllerV2.class).obtenerPorId(Long.valueOf(resenaHerramienta.getIdResenaHerramienta()))).withSelfRel(),
                linkTo(methodOn(ResenaHerramientaControllerV2.class).obtenerResenas()).withRel("resenas"),
                linkTo(methodOn(ResenaHerramientaControllerV2.class).guardarResena(resenaHerramienta)).withRel("crear-resena"),
                linkTo(methodOn(ResenaHerramientaControllerV2.class).actualizarResena(Long.valueOf(resenaHerramienta.getIdResenaHerramienta()), resenaHerramienta)).withRel("actualizar-resena"),
                linkTo(methodOn(ResenaHerramientaControllerV2.class).actualizarResenaParcial(Long.valueOf(resenaHerramienta.getIdResenaHerramienta()), resenaHerramienta)).withRel("actualizar-resena-parcial"),
                linkTo(methodOn(ResenaHerramientaControllerV2.class).eliminarResena(Long.valueOf(resenaHerramienta.getIdResenaHerramienta()))).withRel("eliminar-resena"));
}
}