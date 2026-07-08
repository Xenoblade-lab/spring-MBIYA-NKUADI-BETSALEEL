package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Commande;
import edu.upc.models.dtos.CommandeDto;
import edu.upc.repositories.CommandeRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class CommandeServiceImpl implements CommandeService {

	@Autowired
	private CommandeRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Commande create(CommandeDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Commande row = mapper.mapToCommande(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Commande> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public Commande getById(long id) {
		Commande row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Commande introuvable");
		}
		return row;
	}

	@Override
	public List<Commande> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Commande introuvable");
		}
	}

}
