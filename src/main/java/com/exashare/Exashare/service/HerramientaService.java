package com.exashare.Exashare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exashare.Exashare.model.Arriendo;
import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.model.Resenas;
import com.exashare.Exashare.repository.ArriendoRepository;
import com.exashare.Exashare.repository.HerramientaRepository;
import com.exashare.Exashare.repository.ResenasRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class HerramientaService {

    @Autowired
    private HerramientaRepository HerramientaRepository;

    @Autowired
    private ArriendoRepository arriendoRepository;

    @Autowired
    private ArriendoService arriendoService;

    @Autowired
    private ResenasRepository resenasRepository;

    public List<Herramienta> obtenerHerramientas() {
        return HerramientaRepository.findAll();
    }

    public Herramienta obtenerPorId(long id) {
        return HerramientaRepository.findById(id).orElse(null );
    }

    public Herramienta guardarHerramienta(Herramienta herramienta) {
        return HerramientaRepository.save(herramienta);
    }

    public void eliminarHerramienta(Long id) {

        Herramienta herramienta = HerramientaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Herramienta no encontrada"));

        List<Arriendo> arriendos = arriendoRepository.findByHerramienta(herramienta);
        for (Arriendo arriendo : arriendos) {
        arriendoService.eliminarArriendo(arriendo.getIdArriendo().longValue());
        }

    List<Resenas> resenas = resenasRepository.findByHerramienta(herramienta);
    for (Resenas resena : resenas) {
        resenasRepository.delete(resena);
        }

    HerramientaRepository.delete(herramienta);
    }

    public Herramienta updateHerramienta(Long id, Herramienta Herramienta) {
        Herramienta HerramientaToUpdate = HerramientaRepository.findById(id).orElse(null);
        if (HerramientaToUpdate != null) {
            HerramientaToUpdate.setNombre(Herramienta.getNombre());
            HerramientaToUpdate.setDescripcion(Herramienta.getDescripcion());
            HerramientaToUpdate.setCategoria(Herramienta.getCategoria());
            HerramientaToUpdate.setEstado(Herramienta.getEstado());
            HerramientaToUpdate.setUbicacion(Herramienta.getUbicacion());
            HerramientaToUpdate.setPrecioHerramienta(Herramienta.getPrecioHerramienta());
            return HerramientaRepository.save(HerramientaToUpdate);
        } else {
            return null;
        }
    }

   public Herramienta actualizarHerramientaParcial(Long id, Herramienta herramientaParcial) {
        Herramienta herramientaExistente = HerramientaRepository.findById(id).orElse(null);
        if (herramientaExistente != null) {
            if (herramientaParcial.getNombre() != null) {
                herramientaExistente.setNombre(herramientaParcial.getNombre());
            }
            if (herramientaParcial.getDescripcion() != null) {
                herramientaExistente.setDescripcion(herramientaParcial.getDescripcion());
            }
            if (herramientaParcial.getCategoria() != null) {
                herramientaExistente.setCategoria(herramientaParcial.getCategoria());
            }
            if(herramientaParcial.getEstado() != null) {
                herramientaExistente.setEstado(herramientaParcial.getEstado());
            }
            if (herramientaParcial.getUbicacion() != null) {
                herramientaExistente.setUbicacion(herramientaParcial.getUbicacion());
            }
            if (herramientaParcial.getPrecioHerramienta() != null) {
                herramientaExistente.setPrecioHerramienta(herramientaParcial.getPrecioHerramienta());
            }
            return HerramientaRepository.save(herramientaExistente);
        } else {
            return null;
        }
    }

    public List<Object[]> obtenerHerramientasYUsuarioPorEstado(String estado) {
        return HerramientaRepository.obtenerHerramientasYUsuarioPorEstado(estado);
    }

    public List<Object[]> obtenerHerramientasPorCategoria(String categoria) {
        return HerramientaRepository.obtenerHerramientasPorCategoria(categoria);
    
    }

    public List<Object[]> obtenerHerramientasConCantidadDeArriendos() {
        return HerramientaRepository.obtenerHerramientasConCantidadDeArriendosYResenas();
    }

    public List<Herramienta> buscarPorRangoPrecio(Integer precioMinimo, Integer precioMaximo){
        return HerramientaRepository.findByPrecioHerramientaBetween(precioMinimo, precioMaximo);
        
    }


}