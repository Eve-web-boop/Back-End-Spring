package com.blogpessoal.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.blogpessoal.blogpessoal.model.tema;
import com.blogpessoal.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/temas")
@CrossOrigin("*")
public class temaController {
	@Autowired
	public TemaRepository repositorio;

	@GetMapping
	public ResponseEntity<List<tema>> GetAll() {
		return ResponseEntity.ok(repositorio.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<tema> getById(@PathVariable Long id) {
		return repositorio.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<tema>> getByDescricao(@PathVariable String descricao) {
		return ResponseEntity.ok(repositorio.findAllByDescricaoContainingIgnoreCase(descricao));

	}

	@PostMapping
	public ResponseEntity<tema> post(@Valid @RequestBody tema tema) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repositorio.save(tema));

	}

	@PutMapping
	public ResponseEntity<tema> put(@RequestBody tema tema) {
		return repositorio.findById(tema.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(repositorio.save(tema)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		java.util.Optional<tema> Tema = repositorio.findById(id);

		if (Tema.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		repositorio.deleteById(id);

	}

}
