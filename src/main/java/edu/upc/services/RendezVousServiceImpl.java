package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.RendezVous;
import edu.upc.models.dtos.RendezVousDto;
import edu.upc.repositories.RendezVousRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class RendezVousServiceImpl implements RendezVousService {

	@Autowired
	private RendezVousRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public RendezVous create(RendezVousDto dto, BindingResult result) {
		libraryUtil.validate(result);
		RendezVous row = mapper.mapToRendezVous(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<RendezVous> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public RendezVous getById(long id) {
		RendezVous row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("RendezVous introuvable");
		}
		return row;
	}

	@Override
	public List<RendezVous> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("RendezVous introuvable");
		}
	}

}
