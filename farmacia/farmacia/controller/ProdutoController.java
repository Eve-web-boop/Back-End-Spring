package com.farmacia.farmacia.controller;

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

import com.farmacia.farmacia.model.Produto;
import com.farmacia.farmacia.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*")

public class ProdutoController {

	@Autowired
	public ProdutoRepository repositorio;
	
	@GetMapping
	public ResponseEntity<List<Produto>> GetAll() {
		return ResponseEntity.ok(repositorio.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return repositorio.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/texto/{texto}")
	public ResponseEntity<List<Produto>> getByTexto(@PathVariable String texto) {
		return ResponseEntity.ok(repositorio.findAllByTextoContainingIgnoreCase(texto));

	}
	
	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto Produto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repositorio.save(Produto));

	}

	@PutMapping
	public ResponseEntity<Produto> put(@RequestBody Produto Produto) {
		return repositorio.findById(Produto.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(repositorio.save(Produto)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		java.util.Optional<Produto> Produto = repositorio.findById(id);

		if (Produto.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		repositorio.deleteById(id);

	}

}

