package edu.upc.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import edu.upc.models.Client;
import edu.upc.models.dtos.ClientDto;

public interface ClientService {

	Client create(ClientDto dto, BindingResult result);

	Client update(long id, ClientDto dto, BindingResult result);

	List<Client> delete(long id);

	Client getById(long id);

	List<Client> get();

	List<Client> search(String keyword);

}
