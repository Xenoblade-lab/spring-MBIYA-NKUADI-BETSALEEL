package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Specialite;
import edu.upc.models.dtos.SpecialiteDto;

public interface SpecialiteService {

	Specialite create(SpecialiteDto dto, BindingResult result);

	Specialite update(long id, SpecialiteDto dto, BindingResult result);

	List<Specialite> delete(long id);

	Specialite getById(long id);

	List<Specialite> get();

}
