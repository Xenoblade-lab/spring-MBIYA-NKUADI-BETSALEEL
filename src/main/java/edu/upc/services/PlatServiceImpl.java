package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Plat;
import edu.upc.models.dtos.PlatDto;
import edu.upc.repositories.PlatRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class PlatServiceImpl implements PlatService {

	@Autowired
	private PlatRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Plat create(PlatDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Plat row = mapper.mapToPlat(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Plat update(long id, PlatDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Plat row = mapper.mapToPlat(dto);
		repo.update(id, row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Plat> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public Plat getById(long id) {
		Plat row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Plat introuvable");
		}
		return row;
	}

	@Override
	public List<Plat> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Plat introuvable");
		}
	}

}
