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
public class CommandeDto {

	@Positive(message = "Client invalide")
	private long clientPk;

	@Positive(message = "Plat invalide")
	private long platPk;

	@NotBlank(message = "Note invalide")
	@Size(min = 1, max = 255, message = "Note invalide")
	private String note;

}
