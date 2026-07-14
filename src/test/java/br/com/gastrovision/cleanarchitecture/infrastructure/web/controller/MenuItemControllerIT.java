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
class MenuItemControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private Long restaurantId;

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

        Long ownerId = ((Number) JsonPath.read(userResponse, "$.id")).longValue();

        String restaurantResponse = mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Restaurante\",\"address\":\"Rua 1\",\"cuisineType\":\"Italiana\",\"openingHours\":\"08:00-22:00\",\"ownerId\":" + ownerId + "}"))
                .andReturn().getResponse().getContentAsString();

        restaurantId = ((Number) JsonPath.read(restaurantResponse, "$.id")).longValue();
    }

    private String menuItemJson() {
        return "{\"name\":\"Pizza\",\"description\":\"Pizza margherita\",\"price\":29.90,\"onSiteOnly\":false,\"photoPath\":\"/images/pizza.jpg\"}";
    }

    @Test
    void shouldCreateMenuItem() throws Exception {
        mockMvc.perform(post("/api/restaurants/" + restaurantId + "/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(menuItemJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Pizza"))
                .andExpect(jsonPath("$.price").value(29.90));
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/restaurants/" + restaurantId + "/menu-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldFindMenuItemById() throws Exception {
        String response = mockMvc.perform(post("/api/restaurants/" + restaurantId + "/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(menuItemJson()))
                .andReturn().getResponse().getContentAsString();

        Long itemId = ((Number) JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(get("/api/restaurants/" + restaurantId + "/menu-items/" + itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.name").value("Pizza"));
    }

    @Test
    void shouldUpdateMenuItem() throws Exception {
        String response = mockMvc.perform(post("/api/restaurants/" + restaurantId + "/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(menuItemJson()))
                .andReturn().getResponse().getContentAsString();

        Long itemId = ((Number) JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(put("/api/restaurants/" + restaurantId + "/menu-items/" + itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Pizza Atualizada\",\"description\":\"Nova descrição\",\"price\":39.90,\"onSiteOnly\":true,\"photoPath\":\"/images/pizza2.jpg\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza Atualizada"))
                .andExpect(jsonPath("$.price").value(39.90));
    }

    @Test
    void shouldDeleteMenuItem() throws Exception {
        String response = mockMvc.perform(post("/api/restaurants/" + restaurantId + "/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(menuItemJson()))
                .andReturn().getResponse().getContentAsString();

        Long itemId = ((Number) JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(delete("/api/restaurants/" + restaurantId + "/menu-items/" + itemId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/restaurants/" + restaurantId + "/menu-items/" + itemId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenNotFound() throws Exception {
        mockMvc.perform(get("/api/restaurants/" + restaurantId + "/menu-items/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {
        mockMvc.perform(post("/api/restaurants/" + restaurantId + "/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"desc\",\"price\":29.90,\"onSiteOnly\":false}"))
                .andExpect(status().isBadRequest());
    }
}