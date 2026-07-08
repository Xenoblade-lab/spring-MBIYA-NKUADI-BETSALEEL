package edu.upc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	private static Long specialiteId;
	private static Long patientId;
	private static Long medecinId;
	private static Long rendezVousId;

	private static String suffix;

	private static long readId(MvcResult result) throws Exception {
		Object id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
		return ((Number) id).longValue();
	}

	@Test
	@Order(0)
	void initSuffix() {
		suffix = String.valueOf(System.currentTimeMillis());
	}

	@Test
	@Order(1)
	void specialitesCrud() throws Exception {
		mockMvc.perform(get("/api/specialites"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/specialites")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"libelle\":\"Test Specialite API " + suffix + "\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn();

		specialiteId = readId(created);

		mockMvc.perform(get("/api/specialites/" + specialiteId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.libelle").value("Test Specialite API " + suffix));

		mockMvc.perform(put("/api/specialites/" + specialiteId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"libelle\":\"Test Specialite MAJ " + suffix + "\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.libelle").value("Test Specialite MAJ " + suffix));
	}

	@Test
	@Order(2)
	void patientsApiStillWorks() throws Exception {
		mockMvc.perform(get("/api/patients"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/patients")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"nom\":\"Test Patient API " + suffix + "\",\"age\":32}"))
				.andExpect(status().isCreated())
				.andReturn();

		patientId = readId(created);

		mockMvc.perform(get("/api/patients/" + patientId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.age").value(32));
	}

	@Test
	@Order(3)
	void medecinsWithSpecialiteJoin() throws Exception {
		mockMvc.perform(get("/api/medecins"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].specialite.libelle").exists());

		mockMvc.perform(get("/api/medecins?keyword=Lumumba"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nomComplet").value("Dr. Lumumba Patrick"))
				.andExpect(jsonPath("$[0].specialite.libelle").value("Medecine generale"));

		MvcResult created = mockMvc.perform(post("/api/medecins")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "specialitePk": 1,
								  "nomComplet": "Dr. Test API",
								  "etablissement": "Clinique Test",
								  "email": "test.medecin@clinique.rdc",
								  "anneesExperience": 5
								}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.specialite.libelle").value("Cardiologie"))
				.andReturn();

		medecinId = readId(created);

		mockMvc.perform(get("/api/medecins/" + medecinId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.patients").isArray());
	}

	@Test
	@Order(4)
	void rendezVousEnrollment() throws Exception {
		mockMvc.perform(get("/api/rendezvous"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/rendezvous")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"medecinPk\":" + medecinId + ",\"patientPk\":" + patientId + ",\"motif\":\"Consultation de test API\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.medecin.nomComplet").value("Dr. Test API"))
				.andExpect(jsonPath("$.patient.nom").value("Test Patient API " + suffix))
				.andReturn();

		rendezVousId = readId(created);

		mockMvc.perform(delete("/api/rendezvous/" + rendezVousId))
				.andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void webPages() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/specialites"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/patients"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/medecins"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/medecins?keyword=Lumumba"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/rendezvous"))
				.andExpect(status().isOk());
	}

	@AfterAll
	void cleanupTestData() throws Exception {
		cleanupCreatedEntities();
	}

	private void cleanupCreatedEntities() throws Exception {
		if (rendezVousId != null) {
			mockMvc.perform(delete("/api/rendezvous/" + rendezVousId));
			rendezVousId = null;
		}
		if (medecinId != null) {
			mockMvc.perform(delete("/api/medecins/" + medecinId));
			medecinId = null;
		}
		if (patientId != null) {
			mockMvc.perform(delete("/api/patients/" + patientId));
			patientId = null;
		}
		if (specialiteId != null) {
			mockMvc.perform(delete("/api/specialites/" + specialiteId));
			specialiteId = null;
		}
	}

}
