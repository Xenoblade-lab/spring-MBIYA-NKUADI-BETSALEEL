package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Plat;
import edu.upc.models.dtos.PlatDto;

public interface PlatService {

	Plat create(PlatDto dto, BindingResult result);

	Plat update(long id, PlatDto dto, BindingResult result);

	List<Plat> delete(long id);

	Plat getById(long id);

	List<Plat> get();

}
