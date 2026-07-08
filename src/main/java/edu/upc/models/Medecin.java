package edu.upc.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Medecin implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private long specialitePk;

	private String nomComplet, etablissement, email;

	private int anneesExperience;

	private Specialite specialite;

	@Builder.Default
	private List<Patient> patients = new ArrayList<>();

}
