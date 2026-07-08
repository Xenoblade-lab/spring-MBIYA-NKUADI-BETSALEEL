package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Patient;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class PatientRepoImpl implements PatientRepoCustom {

	private static final String SQL_INSERT = "INSERT INTO patients (nom, age) VALUES (?, ?)";
	private static final String SQL_UPDATE = "UPDATE patients SET nom=?, age=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM patients WHERE id=?";
	private static final String SQL_BY_ID = "SELECT id, nom, age FROM patients WHERE id=?";
	private static final String SQL_ALL = "SELECT id, nom, age FROM patients ORDER BY nom";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Patient entity) {
		Object[] params = new Object[] { entity.getNom(), entity.getAge() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Patient entity) {
		jdbcClient.sql(SQL_UPDATE).params(entity.getNom(), entity.getAge(), id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Patient getById(long id) {
		return sfmHelper.queryOne(SQL_BY_ID, Patient.class, new Object[] { id });
	}

	@Override
	public List<Patient> get() {
		return sfmHelper.query(SQL_ALL, Patient.class);
	}

}
