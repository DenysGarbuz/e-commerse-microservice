package com.denysgarbuz.microservice;

import com.denysgarbuz.microservice.dto.ProductRequest;
import com.denysgarbuz.microservice.model.Product;
import com.denysgarbuz.microservice.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class MicroserviceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.4");
	@Autowired
	private MockMvc mvc;
    @Autowired
    private MockMvc mockMvc;
	@Autowired
	private ProductRepository productRepository;

    @Autowired
	private ObjectMapper objectMapper;

	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	static {
		mongoDBContainer.start();
	}

	@BeforeEach
	void setUp() {
		this.productRepository.deleteAll();


	}


	@Test
	void shouldReturnAllProducts() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
		).andExpect(status().isOk());

	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString)

		).andExpect(status().isCreated());
		assertEquals(1, productRepository.findAll().size());
	}

	private ProductRequest getProductRequest() {
		return new ProductRequest(
				"Iphone",
				"13",
				BigDecimal.valueOf(1200)
		);
	}

}
