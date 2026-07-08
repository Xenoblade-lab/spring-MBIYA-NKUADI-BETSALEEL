package edu.upc.repositories;

import java.util.List;

import edu.upc.models.RendezVous;

public interface RendezVousRepoCustom {

	long create(RendezVous entity);

	void delete(long id);

	RendezVous getById(long id);

	List<RendezVous> get();

}
