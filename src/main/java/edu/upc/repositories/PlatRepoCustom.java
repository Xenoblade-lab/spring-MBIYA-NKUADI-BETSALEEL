package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Plat;

public interface PlatRepoCustom {

	long create(Plat entity);

	void update(long id, Plat entity);

	void delete(long id);

	Plat getById(long id);

	List<Plat> get();

}
