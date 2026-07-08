package edu.upc.models.dtos;

import jakarta.validation.constraints.Email;
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
public class MedecinDto {

	@Positive(message = "Specialite invalide")
	private long specialitePk;

	@NotBlank(message = "Nom complet invalide")
	@Size(min = 1, max = 100, message = "Nom complet invalide")
	private String nomComplet;

	@NotBlank(message = "Etablissement invalide")
	@Size(min = 1, max = 45, message = "Etablissement invalide")
	private String etablissement;

	@NotBlank(message = "Email invalide")
	@Email(message = "Email invalide")
	@Size(max = 80, message = "Email invalide")
	private String email;

	@Positive(message = "Annees d experience invalides")
	private int anneesExperience;

}
