package com.exashare.Exashare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exashare.Exashare.model.Membresia;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresia, Long>{

}
