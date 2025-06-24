package com.exashare.Exashare.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.exashare.Exashare.controller.v2.HerramientaControllerV2;
import com.exashare.Exashare.model.Herramienta;

@Component
public class HerramientaModelAssembler implements RepresentationModelAssembler<Herramienta, EntityModel<Herramienta>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Herramienta> toModel(Herramienta herramienta) {
        return EntityModel.of(herramienta,
                linkTo(methodOn(HerramientaControllerV2.class).obtenerHerramientaPorId(Long.valueOf(herramienta.getIdHerramienta()))).withSelfRel(),
                linkTo(methodOn(HerramientaControllerV2.class).obtenerHerramientas()).withRel("herramientas"),
                linkTo(methodOn(HerramientaControllerV2.class).crearHerramienta(herramienta)).withRel("crear-herramienta"),
                linkTo(methodOn(HerramientaControllerV2.class).actualizarHerramienta(Long.valueOf(herramienta.getIdHerramienta()), herramienta)).withRel("actualizar-herramienta"),
                linkTo(methodOn(HerramientaControllerV2.class).actualizarHerramientaParcial(Long.valueOf(herramienta.getIdHerramienta()), herramienta)).withRel("actualizar-herramienta-parcial"),
                linkTo(methodOn(HerramientaControllerV2.class).eliminarHerramienta(Long.valueOf(herramienta.getIdHerramienta()))).withRel("eliminar-herramienta"),
                linkTo(methodOn(HerramientaControllerV2.class).getHerramientasByPrecioBetween(0, 0)).withRel("rango-precios"));
    }

}
