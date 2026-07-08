package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Plat;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class PlatRepoImpl implements PlatRepoCustom {

	private static final String SQL_INSERT = "INSERT INTO plats (nom, prix_fc) VALUES (?, ?)";
	private static final String SQL_UPDATE = "UPDATE plats SET nom=?, prix_fc=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM plats WHERE id=?";
	private static final String SQL_BY_ID = "SELECT id, nom, prix_fc FROM plats WHERE id=?";
	private static final String SQL_ALL = "SELECT id, nom, prix_fc FROM plats ORDER BY nom";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Plat entity) {
		Object[] params = new Object[] { entity.getNom(), entity.getPrixFc() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Plat entity) {
		jdbcClient.sql(SQL_UPDATE).params(entity.getNom(), entity.getPrixFc(), id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Plat getById(long id) {
		return sfmHelper.queryOne(SQL_BY_ID, Plat.class, new Object[] { id });
	}

	@Override
	public List<Plat> get() {
		return sfmHelper.query(SQL_ALL, Plat.class);
	}

}
