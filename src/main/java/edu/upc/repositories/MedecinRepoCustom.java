package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Patient;
import edu.upc.models.Medecin;

public interface MedecinRepoCustom {

	long create(Medecin entity);

	void update(long id, Medecin entity);

	void delete(long id);

	Medecin getById(long id);

	List<Medecin> getWithSpecialite();

	Medecin getWithSpecialiteById(long id);

	List<Patient> getPatients(long MedecinId);

	List<Medecin> search(String keyword);

}
