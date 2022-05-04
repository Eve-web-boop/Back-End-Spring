package com.blogpessoal.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.blogpessoal.blogpessoal.model.tema;
	
public interface TemaRepository extends JpaRepository<tema, Long>{
	public List <tema> findAllByDescricaoContainingIgnoreCase (@Param("descricao") String descricao);


}
