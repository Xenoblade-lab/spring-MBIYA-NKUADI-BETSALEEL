package edu.upc.utils;

import org.springframework.stereotype.Component;

import edu.upc.models.Patient;
import edu.upc.models.Specialite;
import edu.upc.models.Medecin;
import edu.upc.models.RendezVous;
import edu.upc.models.dtos.PatientDto;
import edu.upc.models.dtos.SpecialiteDto;
import edu.upc.models.dtos.MedecinDto;
import edu.upc.models.dtos.RendezVousDto;

@Component
public class Mapper {

	public Patient mapToPatient(PatientDto dto) {
		return Patient.builder().nom(dto.getNom()).age(dto.getAge()).build();
	}

	public Specialite mapToSpecialite(SpecialiteDto dto) {
		return Specialite.builder().libelle(dto.getLibelle()).build();
	}

	public Medecin mapToMedecin(MedecinDto dto) {
		return Medecin.builder()
				.specialitePk(dto.getSpecialitePk())
				.nomComplet(dto.getNomComplet())
				.etablissement(dto.getEtablissement())
				.email(dto.getEmail())
				.anneesExperience(dto.getAnneesExperience())
				.build();
	}

	public RendezVous mapToRendezVous(RendezVousDto dto) {
		return RendezVous.builder()
				.medecinPk(dto.getMedecinPk())
				.patientPk(dto.getPatientPk())
				.motif(dto.getMotif())
				.build();
	}

	public SpecialiteDto toSpecialiteDto(Specialite entity) {
		return SpecialiteDto.builder().libelle(entity.getLibelle()).build();
	}

	public PatientDto toPatientDto(Patient entity) {
		return PatientDto.builder().nom(entity.getNom()).age(entity.getAge()).build();
	}

	public MedecinDto toMedecinDto(Medecin entity) {
		return MedecinDto.builder()
				.specialitePk(entity.getSpecialitePk())
				.nomComplet(entity.getNomComplet())
				.etablissement(entity.getEtablissement())
				.email(entity.getEmail())
				.anneesExperience(entity.getAnneesExperience())
				.build();
	}

	public RendezVousDto toRendezVousDto(RendezVous entity) {
		return RendezVousDto.builder()
				.medecinPk(entity.getMedecinPk())
				.patientPk(entity.getPatientPk())
				.motif(entity.getMotif())
				.build();
	}

}
