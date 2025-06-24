package com.exashare.Exashare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.model.Herramientas;
import com.exashare.Exashare.model.Usuario;

@Repository
public interface HerramientasRepository extends JpaRepository<Herramientas, Long>{

    List<Herramienta> findByUsuario(Usuario usuario);

    

    
}
