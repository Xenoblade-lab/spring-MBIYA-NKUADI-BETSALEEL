package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Patient;

public interface PatientRepoCustom {

	long create(Patient entity);

	void update(long id, Patient entity);

	void delete(long id);

	Patient getById(long id);

	List<Patient> get();

}
