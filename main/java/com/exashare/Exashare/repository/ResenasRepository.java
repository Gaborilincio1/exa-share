package com.exashare.Exashare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exashare.Exashare.model.Resenas;

@Repository
public interface ResenasRepository extends JpaRepository<Resenas,Long>{



}
