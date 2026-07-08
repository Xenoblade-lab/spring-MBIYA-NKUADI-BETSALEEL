package edu.upc.repositories;

import java.util.List;

import edu.upc.models.Plat;
import edu.upc.models.Client;

public interface ClientRepoCustom {

	long create(Client entity);

	void update(long id, Client entity);

	void delete(long id);

	Client getById(long id);

	List<Client> getWithCategorie();

	Client getWithCategorieById(long id);

	List<Plat> getPlats(long ClientId);

	List<Client> search(String keyword);

}
