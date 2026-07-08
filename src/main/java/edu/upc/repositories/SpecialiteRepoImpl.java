package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Specialite;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class SpecialiteRepoImpl implements SpecialiteRepoCustom {

	private static final String SQL_INSERT = "INSERT INTO specialites (libelle) VALUES (?)";
	private static final String SQL_UPDATE = "UPDATE specialites SET libelle=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM specialites WHERE id=?";
	private static final String SQL_BY_ID = "SELECT id, libelle FROM specialites WHERE id=?";
	private static final String SQL_ALL = "SELECT id, libelle FROM specialites ORDER BY libelle";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Specialite entity) {
		Object[] params = new Object[] { entity.getLibelle() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Specialite entity) {
		jdbcClient.sql(SQL_UPDATE).params(entity.getLibelle(), id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Specialite getById(long id) {
		return sfmHelper.queryOne(SQL_BY_ID, Specialite.class, new Object[] { id });
	}

	@Override
	public List<Specialite> get() {
		return sfmHelper.query(SQL_ALL, Specialite.class);
	}

}
