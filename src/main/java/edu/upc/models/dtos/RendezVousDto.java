package edu.upc.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class RendezVousDto {

	@Positive(message = "Medecin invalide")
	private long medecinPk;

	@Positive(message = "Patient invalide")
	private long patientPk;

	@NotBlank(message = "Motif invalide")
	@Size(min = 1, max = 255, message = "Motif invalide")
	private String motif;

}
