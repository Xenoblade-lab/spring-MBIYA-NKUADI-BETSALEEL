package edu.upc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import edu.upc.models.Patient;
import edu.upc.models.dtos.PatientDto;
import edu.upc.repositories.PatientRepoCustom;
import edu.upc.utils.Mapper;
import edu.upc.utils.MyLibraryUtil;
import edu.upc.utils.exceptions.NotFoundException;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepoCustom repo;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MyLibraryUtil libraryUtil;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Patient create(PatientDto dto, BindingResult result) {
		libraryUtil.validate(result);
		Patient row = mapper.mapToPatient(dto);
		long id = repo.create(row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Patient update(long id, PatientDto dto, BindingResult result) {
		libraryUtil.validate(result);
		requireExists(id);
		Patient row = mapper.mapToPatient(dto);
		repo.update(id, row);
		return repo.getById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Patient> delete(long id) {
		requireExists(id);
		repo.delete(id);
		return repo.get();
	}

	@Override
	public Patient getById(long id) {
		Patient row = repo.getById(id);
		if (row == null) {
			throw new NotFoundException("Patient introuvable");
		}
		return row;
	}

	@Override
	public List<Patient> get() {
		return repo.get();
	}

	private void requireExists(long id) {
		if (repo.getById(id) == null) {
			throw new NotFoundException("Patient introuvable");
		}
	}

}
