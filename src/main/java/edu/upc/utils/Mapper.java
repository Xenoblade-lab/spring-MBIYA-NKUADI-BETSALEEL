package edu.upc.utils;

import org.springframework.stereotype.Component;

import edu.upc.models.Plat;
import edu.upc.models.Categorie;
import edu.upc.models.Client;
import edu.upc.models.Commande;
import edu.upc.models.dtos.PlatDto;
import edu.upc.models.dtos.CategorieDto;
import edu.upc.models.dtos.ClientDto;
import edu.upc.models.dtos.CommandeDto;

@Component
public class Mapper {

	public Plat mapToPlat(PlatDto dto) {
		return Plat.builder().nom(dto.getNom()).prixFc(dto.getPrixFc()).build();
	}

	public Categorie mapToCategorie(CategorieDto dto) {
		return Categorie.builder().libelle(dto.getLibelle()).build();
	}

	public Client mapToClient(ClientDto dto) {
		return Client.builder()
				.categoriePk(dto.getCategoriePk())
				.nomComplet(dto.getNomComplet())
				.quartier(dto.getQuartier())
				.email(dto.getEmail())
				.pointsFidelite(dto.getPointsFidelite())
				.build();
	}

	public Commande mapToCommande(CommandeDto dto) {
		return Commande.builder()
				.clientPk(dto.getClientPk())
				.platPk(dto.getPlatPk())
				.note(dto.getNote())
				.build();
	}

	public CategorieDto toCategorieDto(Categorie entity) {
		return CategorieDto.builder().libelle(entity.getLibelle()).build();
	}

	public PlatDto toPlatDto(Plat entity) {
		return PlatDto.builder().nom(entity.getNom()).prixFc(entity.getPrixFc()).build();
	}

	public ClientDto toClientDto(Client entity) {
		return ClientDto.builder()
				.categoriePk(entity.getCategoriePk())
				.nomComplet(entity.getNomComplet())
				.quartier(entity.getQuartier())
				.email(entity.getEmail())
				.pointsFidelite(entity.getPointsFidelite())
				.build();
	}

	public CommandeDto toCommandeDto(Commande entity) {
		return CommandeDto.builder()
				.clientPk(entity.getClientPk())
				.platPk(entity.getPlatPk())
				.note(entity.getNote())
				.build();
	}

}
