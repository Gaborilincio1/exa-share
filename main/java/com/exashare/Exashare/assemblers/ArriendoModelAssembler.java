package com.exashare.Exashare.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.exashare.Exashare.controller.v2.ArriendoControllerV2;
import com.exashare.Exashare.model.Arriendo;

@Component
public class ArriendoModelAssembler implements RepresentationModelAssembler<Arriendo, EntityModel<Arriendo>> {

    @SuppressWarnings("null")
    @Override
    public EntityModel<Arriendo> toModel(Arriendo arriendo) {
        return EntityModel.of(arriendo,
                linkTo(methodOn(ArriendoControllerV2.class).obtenerArriendoPorId(Long.valueOf(arriendo.getIdArriendo()))).withSelfRel(),
                linkTo(methodOn(ArriendoControllerV2.class).obtenerArriendos()).withRel("arriendos"),
                linkTo(methodOn(ArriendoControllerV2.class).crearArriendo(arriendo)).withRel("crear-arriendo"),
                linkTo(methodOn(ArriendoControllerV2.class).actualizarArriendo(Long.valueOf(arriendo.getIdArriendo()), arriendo)).withRel("actualizar-arriendo"),
                linkTo(methodOn(ArriendoControllerV2.class).actualizarArriendoParcial(Long.valueOf(arriendo.getIdArriendo()), arriendo)).withRel("actualizar-arriendo-parcial"),
                linkTo(methodOn(ArriendoControllerV2.class).eliminarArriendo(Long.valueOf(arriendo.getIdArriendo()))).withRel("eliminar-arriendo"),
                linkTo(methodOn(ArriendoControllerV2.class).getArriendosByFechaInicioBetween(arriendo.getFechaInicio(), arriendo.getFechaFin())).withRel("arriendos-por-fechaInicio"),
                linkTo(methodOn(ArriendoControllerV2.class).getArriendosByEstadoAndFechaInicioBetween(arriendo.getEstado(), arriendo.getFechaInicio(), arriendo.getFechaFin())).withRel("arriendos-por-estado-y-fechaInicio"),
                linkTo(methodOn(ArriendoControllerV2.class).getArriendosConUsuarioYHerramienta()).withRel("herramientas-por-usuario-y-arriendo"));
    }

}
