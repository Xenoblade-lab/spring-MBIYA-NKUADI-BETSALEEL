package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Commande;
import edu.upc.models.dtos.CommandeDto;

public interface CommandeService {

	Commande create(CommandeDto dto, BindingResult result);

	List<Commande> delete(long id);

	Commande getById(long id);

	List<Commande> get();

}
