package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Commande;

public interface CommandeRepoCustom {

	long create(Commande entity);

	void delete(long id);

	Commande getById(long id);

	List<Commande> get();

}
