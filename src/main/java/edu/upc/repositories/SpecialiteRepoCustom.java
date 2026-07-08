package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Specialite;

public interface SpecialiteRepoCustom {

	long create(Specialite entity);

	void update(long id, Specialite entity);

	void delete(long id);

	Specialite getById(long id);

	List<Specialite> get();

}
