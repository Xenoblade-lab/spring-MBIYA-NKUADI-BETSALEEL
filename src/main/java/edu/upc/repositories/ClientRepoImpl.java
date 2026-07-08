package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Plat;
import edu.upc.models.Client;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class ClientRepoImpl implements ClientRepoCustom {

	private static final String SQL_INSERT = """
			INSERT INTO clients (categorie_pk, nom_complet, quartier, email, points_fidelite)
			VALUES (?, ?, ?, ?, ?)
			""";

	private static final String SQL_UPDATE = """
			UPDATE clients
			SET categorie_pk=?, nom_complet=?, quartier=?, email=?, points_fidelite=?
			WHERE id=?
			""";

	private static final String SQL_DELETE = "DELETE FROM clients WHERE id=?";

	private static final String SQL_BY_ID = """
			SELECT c.id, c.categorie_pk, c.nom_complet, c.quartier, c.email, c.points_fidelite,
			       cat.id categorie_id, cat.libelle categorie_libelle
			FROM clients c
			INNER JOIN categories cat ON c.categorie_pk = cat.id
			WHERE c.id = ?
			""";

	private static final String SQL_WITH_CATEGORIE = """
			SELECT c.id, c.categorie_pk, c.nom_complet, c.quartier, c.email, c.points_fidelite,
			       cat.id categorie_id, cat.libelle categorie_libelle
			FROM clients c
			INNER JOIN categories cat ON c.categorie_pk = cat.id
			ORDER BY c.nom_complet
			""";

	private static final String SQL_SEARCH = """
			SELECT c.id, c.categorie_pk, c.nom_complet, c.quartier, c.email, c.points_fidelite,
			       cat.id categorie_id, cat.libelle categorie_libelle
			FROM clients c
			INNER JOIN categories cat ON c.categorie_pk = cat.id
			WHERE c.nom_complet LIKE ?
			ORDER BY c.nom_complet
			""";

	private static final String SQL_PLATS = """
			SELECT b.id, b.nom, b.prix_fc
			FROM plats b
			INNER JOIN commandes v ON v.plat_pk = b.id
			WHERE v.client_pk = ?
			ORDER BY b.nom
			""";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Client entity) {
		Object[] params = new Object[] {
				entity.getCategoriePk(),
				entity.getNomComplet(),
				entity.getQuartier(),
				entity.getEmail(),
				entity.getPointsFidelite()
		};
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Client entity) {
		jdbcClient.sql(SQL_UPDATE).params(
				entity.getCategoriePk(),
				entity.getNomComplet(),
				entity.getQuartier(),
				entity.getEmail(),
				entity.getPointsFidelite(),
				id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Client getById(long id) {
		return getWithCategorieById(id);
	}

	@Override
	public List<Client> getWithCategorie() {
		return sfmHelper.queryJoin(SQL_WITH_CATEGORIE, Client.class, "id", "categorie_id");
	}

	@Override
	public Client getWithCategorieById(long id) {
		Client row = sfmHelper.queryJoinOne(SQL_BY_ID, Client.class, new Object[] { id }, "id", "categorie_id");
		if (row != null) {
			row.setPlats(getPlats(id));
		}
		return row;
	}

	@Override
	public List<Plat> getPlats(long clientId) {
		return sfmHelper.query(SQL_PLATS, Plat.class, new Object[] { clientId });
	}

	@Override
	public List<Client> search(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return getWithCategorie();
		}
		return sfmHelper.queryJoin(SQL_SEARCH, Client.class, new Object[] { "%" + keyword.trim() + "%" }, "id",
				"categorie_id");
	}

}
