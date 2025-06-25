package com.exashare.Exashare.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.exashare.Exashare.controller.v2.HerramientasControllerV2;
import com.exashare.Exashare.model.Herramientas;


@Component
public class HerramientasModelAssembler implements RepresentationModelAssembler<Herramientas, EntityModel<Herramientas>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Herramientas> toModel(Herramientas herramientas) {
        return EntityModel.of(herramientas,
                linkTo(methodOn(HerramientasControllerV2.class).obtenerHerramientasPorId(Long.valueOf(herramientas.getIdHerramientas()))).withSelfRel(),
                linkTo(methodOn(HerramientasControllerV2.class).obtenerHerramientas()).withRel("herraminentas"),
                linkTo(methodOn(HerramientasControllerV2.class).guardarHerramientas(herramientas)).withRel("crear-herramientas"),
                linkTo(methodOn(HerramientasControllerV2.class).updateHerramientas(Long.valueOf(herramientas.getIdHerramientas()), herramientas)).withRel("actualizar-herramienta"),
                linkTo(methodOn(HerramientasControllerV2.class).actualizarHerramientasParcial(Long.valueOf(herramientas.getIdHerramientas()), herramientas)).withRel("actualizar-herramienta-parcial"),
                linkTo(methodOn(HerramientasControllerV2.class).eliminarHerramientas(Long.valueOf(herramientas.getIdHerramientas()))).withRel("eliminar-herramienta"));
        
    }

}
