package com.exashare.Exashare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exashare.Exashare.model.ResenaHerramienta;

@Repository
public interface ResenaHerramientaRepository extends JpaRepository<ResenaHerramienta,Long> {

}
