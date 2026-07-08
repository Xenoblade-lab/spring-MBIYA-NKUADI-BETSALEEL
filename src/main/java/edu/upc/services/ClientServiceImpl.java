package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Client;
import edu.upc.models.dtos.ClientDto;
import edu.upc.repositories.ClientRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Client create(ClientDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Client row = mapper.mapToClient(dto);
		long id = repo.create(row);
		return repo.getWithCategorieById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Client update(long id, ClientDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Client row = mapper.mapToClient(dto);
		repo.update(id, row);
		return repo.getWithCategorieById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Client> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.getWithCategorie();
	}

	@Override
	public Client getById(long id) {
		Client row = repo.getWithCategorieById(id);
		if (row == null) {
			throw new NotFoundException("Client introuvable");
		}
		return row;
	}

	@Override
	public List<Client> get() {
		return repo.getWithCategorie();
	}

	@Override
	public List<Client> search(String keyword) {
		return repo.search(keyword);
	}

	private void requireExists(long id) {
		if (repo.getWithCategorieById(id) == null) {
			throw new NotFoundException("Client introuvable");
		}
	}

}
