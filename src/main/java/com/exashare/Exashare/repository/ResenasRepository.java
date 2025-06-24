package com.exashare.Exashare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exashare.Exashare.model.Arriendo;
import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.model.ResenaUsuario;
import com.exashare.Exashare.model.Resenas;
import com.exashare.Exashare.model.Usuario;

@Repository
public interface ResenasRepository extends JpaRepository<Resenas,Long>{

    List<Resenas> findByUsuario(Usuario usuario);

    List<Resenas> findByHerramienta (Herramienta herramienta);

    List<Resenas> findByArriendo(Arriendo arriendo);
    
    List<Resenas> findByResenaUsuario(ResenaUsuario resenaUsuario);

}
