package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.RendezVous;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class RendezVousRepoImpl implements RendezVousRepoCustom {

	private static final String SQL_INSERT = """
			INSERT INTO rendezvous (medecin_pk, patient_pk, motif) VALUES (?, ?, ?)
			""";

	private static final String SQL_DELETE = "DELETE FROM rendezvous WHERE id=?";

	private static final String SQL_BY_ID = """
			SELECT r.id, r.medecin_pk, r.patient_pk, r.motif,
			       m.id medecin_id, m.nom_complet medecin_nom_complet,
			       p.id patient_id, p.nom patient_nom, p.age patient_age
			FROM rendezvous r
			INNER JOIN medecins m ON r.medecin_pk = m.id
			INNER JOIN patients p ON r.patient_pk = p.id
			WHERE r.id = ?
			""";

	private static final String SQL_ALL = """
			SELECT r.id, r.medecin_pk, r.patient_pk, r.motif,
			       m.id medecin_id, m.nom_complet medecin_nom_complet,
			       p.id patient_id, p.nom patient_nom, p.age patient_age
			FROM rendezvous r
			INNER JOIN medecins m ON r.medecin_pk = m.id
			INNER JOIN patients p ON r.patient_pk = p.id
			ORDER BY m.nom_complet, p.nom
			""";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(RendezVous entity) {
		Object[] params = new Object[] { entity.getMedecinPk(), entity.getPatientPk(), entity.getMotif() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public RendezVous getById(long id) {
		return sfmHelper.queryJoinOne(SQL_BY_ID, RendezVous.class, new Object[] { id }, "id", "medecin_id", "patient_id");
	}

	@Override
	public List<RendezVous> get() {
		return sfmHelper.queryJoin(SQL_ALL, RendezVous.class, "id", "medecin_id", "patient_id");
	}

}
