package com.example.demo.controller;

import java.util.List;

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

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Pessoa;
import com.example.demo.repository.PessoaRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

	@RestController
	@RequestMapping("/pessoas")
	@Api(value = "API REST Pessoas")
	@CrossOrigin(origins = "*")
	public class PessoaController {
		
		@Autowired
		private PessoaRepository pessoaRepository;
		
		@GetMapping
		@ApiOperation(value = "Retorna uma lista de pessoas")
		public List<Pessoa> listar() {
			return pessoaRepository.findAll();
		}
		
		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)
		@ApiOperation(value = "Insere uma nova pessoa")
		public Pessoa adicionar(@RequestBody Pessoa pessoa) {
			return pessoaRepository.save(pessoa);
		}
		
		@PutMapping("/pessoas/{id}")
		@ApiOperation(value = "Atualiza determinada pessoa")
		public ResponseEntity<Pessoa> atualizar(@PathVariable(value = "id") Long id,
				@RequestBody Pessoa pessoaDetalhes) throws ResourceNotFoundException {
			Pessoa pessoa = pessoaRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id :: " + id));
			pessoa.setNome(pessoaDetalhes.getNome());
			pessoaRepository.save(pessoa);
			return ResponseEntity.ok().body(pessoa);
		}
		
		@DeleteMapping("/pessoas/{id}")
		@ApiOperation(value = "Deleta determinada pessoa")
		public ResponseEntity<?> deletar(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
			Pessoa pessoa = pessoaRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com id :: " + id));
			pessoaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
	}

