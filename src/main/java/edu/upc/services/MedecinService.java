package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Medecin;
import edu.upc.models.dtos.MedecinDto;

public interface MedecinService {

	Medecin create(MedecinDto dto, BindingResult result);

	Medecin update(long id, MedecinDto dto, BindingResult result);

	List<Medecin> delete(long id);

	Medecin getById(long id);

	List<Medecin> get();

	List<Medecin> search(String keyword);

}
