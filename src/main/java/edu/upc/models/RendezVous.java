package edu.upc.models;

import java.io.Serializable;

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
public class RendezVous implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private long medecinPk, patientPk;

	private String motif;

	private Medecin medecin;

	private Patient patient;

}
