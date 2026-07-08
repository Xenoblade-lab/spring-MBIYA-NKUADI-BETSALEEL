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
public class ClientDto {

	@Positive(message = "Catégorie invalide")
	private long categoriePk;

	@NotBlank(message = "Nom complet invalide")
	@Size(min = 1, max = 100, message = "Nom complet invalide")
	private String nomComplet;

	@NotBlank(message = "Quartier invalide")
	@Size(min = 1, max = 45, message = "Quartier invalide")
	private String quartier;

	@NotBlank(message = "Email invalide")
	@Email(message = "Email invalide")
	@Size(max = 80, message = "Email invalide")
	private String email;

	@Positive(message = "Points fidélité invalides")
	private int pointsFidelite;

}
