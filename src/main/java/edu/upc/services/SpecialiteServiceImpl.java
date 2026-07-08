package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Specialite;
import edu.upc.models.dtos.SpecialiteDto;
import edu.upc.repositories.SpecialiteRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class SpecialiteServiceImpl implements SpecialiteService {

	@Autowired
	private SpecialiteRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Specialite create(SpecialiteDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Specialite row = mapper.mapToSpecialite(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Specialite update(long id, SpecialiteDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Specialite row = mapper.mapToSpecialite(dto);
		repo.update(id, row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Specialite> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public Specialite getById(long id) {
		Specialite row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Catégorie introuvable");
		}
		return row;
	}

	@Override
	public List<Specialite> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Catégorie introuvable");
		}
	}

}
