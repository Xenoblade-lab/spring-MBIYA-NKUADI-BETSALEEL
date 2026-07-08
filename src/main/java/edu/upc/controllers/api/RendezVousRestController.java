package edu.upc.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.upc.models.RendezVous;
import edu.upc.models.dtos.RendezVousDto;
import edu.upc.services.RendezVousService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rendezvous")
public class RendezVousRestController {

	@Autowired
	private RendezVousService service;

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody RendezVousDto dto, BindingResult result) {
		RendezVous row = service.create(dto, result);
		return new ResponseEntity<>(row, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		List<RendezVous> data = service.delete(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") long id) {
		return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> get() {
		return new ResponseEntity<>(service.get(), HttpStatus.OK);
	}

}
