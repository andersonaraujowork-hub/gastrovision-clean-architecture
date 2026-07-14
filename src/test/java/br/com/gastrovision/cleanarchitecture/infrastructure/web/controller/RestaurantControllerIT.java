package br.com.gastrovision.cleanarchitecture.infrastructure.web.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RestaurantControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private Long ownerId;

    @BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        String userTypeResponse = mockMvc.perform(post("/api/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Dono\"}"))
                .andReturn().getResponse().getContentAsString();

        Long userTypeId = ((Number) JsonPath.read(userTypeResponse, "$.id")).longValue();

        String userResponse = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Dono\",\"email\":\"dono@email.com\",\"userTypeId\":" + userTypeId + "}"))
                .andReturn().getResponse().getContentAsString();

        ownerId = ((Number) JsonPath.read(userResponse, "$.id")).longValue();
    }

    private String restaurantJson() {
        return "{\"name\":\"Restaurante A\",\"address\":\"Rua 1\",\"cuisineType\":\"Italiana\",\"openingHours\":\"08:00-22:00\",\"ownerId\":" + ownerId + "}";
    }

    @Test
    void shouldCreateRestaurant() throws Exception {
        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Restaurante A"))
                .andExpect(jsonPath("$.cuisineType").value("Italiana"));
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldFindRestaurantById() throws Exception {
        String response = mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson()))
                .andReturn().getResponse().getContentAsString();

        Long restaurantId = ((Number) JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(get("/api/restaurants/" + restaurantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurantId))
                .andExpect(jsonPath("$.name").value("Restaurante A"));
    }

    @Test
    void shouldUpdateRestaurant() throws Exception {
        String response = mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson()))
                .andReturn().getResponse().getContentAsString();

        Long restaurantId = ((Number) JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(put("/api/restaurants/" + restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Restaurante B\",\"address\":\"Rua 2\",\"cuisineType\":\"Japonesa\",\"openingHours\":\"10:00-20:00\",\"ownerId\":" + ownerId + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Restaurante B"))
                .andExpect(jsonPath("$.cuisineType").value("Japonesa"));
    }

    @Test
    void shouldDeleteRestaurant() throws Exception {
        String response = mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson()))
                .andReturn().getResponse().getContentAsString();

        Long restaurantId = ((Number) JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(delete("/api/restaurants/" + restaurantId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/restaurants/" + restaurantId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenNotFound() throws Exception {
        mockMvc.perform(get("/api/restaurants/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {
        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"address\":\"Rua 1\",\"cuisineType\":\"Italiana\",\"openingHours\":\"08:00-22:00\",\"ownerId\":" + ownerId + "}"))
                .andExpect(status().isBadRequest());
    }
}
