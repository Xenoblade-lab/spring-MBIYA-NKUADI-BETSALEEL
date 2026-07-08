package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Patient;
import edu.upc.models.Medecin;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class MedecinRepoImpl implements MedecinRepoCustom {

	private static final String SQL_INSERT = """
			INSERT INTO medecins (specialite_pk, nom_complet, etablissement, email, annees_experience)
			VALUES (?, ?, ?, ?, ?)
			""";

	private static final String SQL_UPDATE = """
			UPDATE medecins
			SET specialite_pk=?, nom_complet=?, etablissement=?, email=?, annees_experience=?
			WHERE id=?
			""";

	private static final String SQL_DELETE = "DELETE FROM medecins WHERE id=?";

	private static final String SQL_BY_ID = """
			SELECT m.id, m.specialite_pk, m.nom_complet, m.etablissement, m.email, m.annees_experience,
			       s.id specialite_id, s.libelle specialite_libelle
			FROM medecins m
			INNER JOIN specialites s ON m.specialite_pk = s.id
			WHERE m.id = ?
			""";

	private static final String SQL_WITH_SPECIALITE = """
			SELECT m.id, m.specialite_pk, m.nom_complet, m.etablissement, m.email, m.annees_experience,
			       s.id specialite_id, s.libelle specialite_libelle
			FROM medecins m
			INNER JOIN specialites s ON m.specialite_pk = s.id
			ORDER BY m.nom_complet
			""";

	private static final String SQL_SEARCH = """
			SELECT m.id, m.specialite_pk, m.nom_complet, m.etablissement, m.email, m.annees_experience,
			       s.id specialite_id, s.libelle specialite_libelle
			FROM medecins m
			INNER JOIN specialites s ON m.specialite_pk = s.id
			WHERE m.nom_complet LIKE ?
			ORDER BY m.nom_complet
			""";

	private static final String SQL_PATIENTS = """
			SELECT p.id, p.nom, p.age
			FROM patients p
			INNER JOIN rendezvous r ON r.patient_pk = p.id
			WHERE r.medecin_pk = ?
			ORDER BY p.nom
			""";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Medecin entity) {
		Object[] params = new Object[] {
				entity.getSpecialitePk(),
				entity.getNomComplet(),
				entity.getEtablissement(),
				entity.getEmail(),
				entity.getAnneesExperience()
		};
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Medecin entity) {
		jdbcClient.sql(SQL_UPDATE).params(
				entity.getSpecialitePk(),
				entity.getNomComplet(),
				entity.getEtablissement(),
				entity.getEmail(),
				entity.getAnneesExperience(),
				id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Medecin getById(long id) {
		return getWithSpecialiteById(id);
	}

	@Override
	public List<Medecin> getWithSpecialite() {
		return sfmHelper.queryJoin(SQL_WITH_SPECIALITE, Medecin.class, "id", "specialite_id");
	}

	@Override
	public Medecin getWithSpecialiteById(long id) {
		Medecin row = sfmHelper.queryJoinOne(SQL_BY_ID, Medecin.class, new Object[] { id }, "id", "specialite_id");
		if (row != null) {
			row.setPatients(getPatients(id));
		}
		return row;
	}

	@Override
	public List<Patient> getPatients(long medecinId) {
		return sfmHelper.query(SQL_PATIENTS, Patient.class, new Object[] { medecinId });
	}

	@Override
	public List<Medecin> search(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return getWithSpecialite();
		}
		return sfmHelper.queryJoin(SQL_SEARCH, Medecin.class, new Object[] { "%" + keyword.trim() + "%" }, "id",
				"specialite_id");
	}

}
