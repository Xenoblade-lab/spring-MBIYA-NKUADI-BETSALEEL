package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.RendezVous;
import edu.upc.models.dtos.RendezVousDto;

public interface RendezVousService {

	RendezVous create(RendezVousDto dto, BindingResult result);

	List<RendezVous> delete(long id);

	RendezVous getById(long id);

	List<RendezVous> get();

}
