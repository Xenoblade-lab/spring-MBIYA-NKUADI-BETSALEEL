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

	private static Long categorieId;
	private static Long platId;
	private static Long clientId;
	private static Long commandeId;

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
	void categoriesCrud() throws Exception {
		mockMvc.perform(get("/api/categories"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/categories")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"libelle\":\"Test Categorie API " + suffix + "\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn();

		categorieId = readId(created);

		mockMvc.perform(get("/api/categories/" + categorieId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.libelle").value("Test Categorie API " + suffix));

		mockMvc.perform(put("/api/categories/" + categorieId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"libelle\":\"Test Categorie MAJ " + suffix + "\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.libelle").value("Test Categorie MAJ " + suffix));
	}

	@Test
	@Order(2)
	void platsApiStillWorks() throws Exception {
		mockMvc.perform(get("/api/plats"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/plats")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"nom\":\"Test Plat API " + suffix + "\",\"prixFc\":15000}"))
				.andExpect(status().isCreated())
				.andReturn();

		platId = readId(created);

		mockMvc.perform(get("/api/plats/" + platId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.prixFc").value(15000));
	}

	@Test
	@Order(3)
	void clientsWithCategorieJoin() throws Exception {
		mockMvc.perform(get("/api/clients"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].categorie.libelle").exists());

		mockMvc.perform(get("/api/clients?keyword=Mbiya"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nomComplet").value("Mbiya Nkuadi Betsaleel"))
				.andExpect(jsonPath("$[0].categorie.libelle").value("VIP"));

		MvcResult created = mockMvc.perform(post("/api/clients")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "categoriePk": 1,
								  "nomComplet": "Test Client API",
								  "quartier": "Gombe",
								  "email": "test.client@restaurant.rdc",
								  "pointsFidelite": 40
								}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.categorie.libelle").value("Bronze"))
				.andReturn();

		clientId = readId(created);

		mockMvc.perform(get("/api/clients/" + clientId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.plats").isArray());
	}

	@Test
	@Order(4)
	void commandeEnrollment() throws Exception {
		mockMvc.perform(get("/api/commandes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));

		MvcResult created = mockMvc.perform(post("/api/commandes")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"clientPk\":" + clientId + ",\"platPk\":" + platId + ",\"note\":\"Excellent plat de test !\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.client.nomComplet").value("Test Client API"))
				.andExpect(jsonPath("$.plat.nom").value("Test Plat API " + suffix))
				.andReturn();

		commandeId = readId(created);

		mockMvc.perform(delete("/api/commandes/" + commandeId))
				.andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void webPages() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/categories"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/plats"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/clients"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/clients?keyword=Mbiya"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/commandes"))
				.andExpect(status().isOk());
	}

	@AfterAll
	void cleanupTestData() throws Exception {
		cleanupCreatedEntities();
	}

	private void cleanupCreatedEntities() throws Exception {
		if (commandeId != null) {
			mockMvc.perform(delete("/api/commandes/" + commandeId));
			commandeId = null;
		}
		if (clientId != null) {
			mockMvc.perform(delete("/api/clients/" + clientId));
			clientId = null;
		}
		if (platId != null) {
			mockMvc.perform(delete("/api/plats/" + platId));
			platId = null;
		}
		if (categorieId != null) {
			mockMvc.perform(delete("/api/categories/" + categorieId));
			categorieId = null;
		}
	}

}
