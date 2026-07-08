package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Commande;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class CommandeRepoImpl implements CommandeRepoCustom {

	private static final String SQL_INSERT = """
			INSERT INTO commandes (client_pk, plat_pk, note) VALUES (?, ?, ?)
			""";

	private static final String SQL_DELETE = "DELETE FROM commandes WHERE id=?";

	private static final String SQL_BY_ID = """
			SELECT v.id, v.client_pk, v.plat_pk, v.note,
			       c.id client_id, c.nom_complet client_nom_complet,
			       b.id plat_id, b.nom plat_nom, b.prix_fc plat_prix_fc
			FROM commandes v
			INNER JOIN clients c ON v.client_pk = c.id
			INNER JOIN plats b ON v.plat_pk = b.id
			WHERE v.id = ?
			""";

	private static final String SQL_ALL = """
			SELECT v.id, v.client_pk, v.plat_pk, v.note,
			       c.id client_id, c.nom_complet client_nom_complet,
			       b.id plat_id, b.nom plat_nom, b.prix_fc plat_prix_fc
			FROM commandes v
			INNER JOIN clients c ON v.client_pk = c.id
			INNER JOIN plats b ON v.plat_pk = b.id
			ORDER BY c.nom_complet, b.nom
			""";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Commande entity) {
		Object[] params = new Object[] { entity.getClientPk(), entity.getPlatPk(), entity.getNote() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Commande getById(long id) {
		return sfmHelper.queryJoinOne(SQL_BY_ID, Commande.class, new Object[] { id }, "id", "client_id", "plat_id");
	}

	@Override
	public List<Commande> get() {
		return sfmHelper.queryJoin(SQL_ALL, Commande.class, "id", "client_id", "plat_id");
	}

}
