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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.upc.models.Patient;
import edu.upc.models.dtos.PatientDto;
import edu.upc.services.PatientService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class PatientRestController {

	@Autowired
	private PatientService service;

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody PatientDto dto, BindingResult result) {
		Patient row = service.create(dto, result);
		return new ResponseEntity<>(row, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @Valid @RequestBody PatientDto dto,
			BindingResult result) {
		Patient row = service.update(id, dto, result);
		return new ResponseEntity<>(row, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		List<Patient> data = service.delete(id);
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
