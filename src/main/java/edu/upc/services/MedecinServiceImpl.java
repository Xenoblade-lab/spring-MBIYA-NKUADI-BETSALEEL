package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Medecin;
import edu.upc.models.dtos.MedecinDto;
import edu.upc.repositories.MedecinRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class MedecinServiceImpl implements MedecinService {

	@Autowired
	private MedecinRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Medecin create(MedecinDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Medecin row = mapper.mapToMedecin(dto);
		long id = repo.create(row);
		return repo.getWithSpecialiteById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Medecin update(long id, MedecinDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Medecin row = mapper.mapToMedecin(dto);
		repo.update(id, row);
		return repo.getWithSpecialiteById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Medecin> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.getWithSpecialite();
	}

	@Override
	public Medecin getById(long id) {
		Medecin row = repo.getWithSpecialiteById(id);
		if (row == null) {
			throw new NotFoundException("Medecin introuvable");
		}
		return row;
	}

	@Override
	public List<Medecin> get() {
		return repo.getWithSpecialite();
	}

	@Override
	public List<Medecin> search(String keyword) {
		return repo.search(keyword);
	}

	private void requireExists(long id) {
		if (repo.getWithSpecialiteById(id) == null) {
			throw new NotFoundException("Medecin introuvable");
		}
	}

}
