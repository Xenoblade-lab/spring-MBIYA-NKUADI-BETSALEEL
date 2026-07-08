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
public class Commande implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private long clientPk, platPk;

	private String note;

	private Client client;

	private Plat plat;

}
