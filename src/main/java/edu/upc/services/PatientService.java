package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Patient;
import edu.upc.models.dtos.PatientDto;

public interface PatientService {

	Patient create(PatientDto dto, BindingResult result);

	Patient update(long id, PatientDto dto, BindingResult result);

	List<Patient> delete(long id);

	Patient getById(long id);

	List<Patient> get();

}
