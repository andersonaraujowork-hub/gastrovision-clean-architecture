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
class UserControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private Long userTypeId;

    @BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        String response = mockMvc.perform(post("/api/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Cliente\"}"))
                .andReturn().getResponse().getContentAsString();

        userTypeId = ((Number) JsonPath.read(response, "$.id")).longValue();
    }

    @Test
    void shouldCreateUser() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"João\",\"email\":\"joao@email.com\",\"userTypeId\":" + userTypeId + "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("João"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldFindUserById() throws Exception {
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"João\",\"email\":\"joao@email.com\",\"userTypeId\":" + userTypeId + "}"))
                .andReturn().getResponse().getContentAsString();

        Long userId = ((Number) JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("João"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"João\",\"email\":\"joao@email.com\",\"userTypeId\":" + userTypeId + "}"))
                .andReturn().getResponse().getContentAsString();

        Long userId = ((Number) JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"João Atualizado\",\"email\":\"joao.novo@email.com\",\"userTypeId\":" + userTypeId + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Atualizado"))
                .andExpect(jsonPath("$.email").value("joao.novo@email.com"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"João\",\"email\":\"joao@email.com\",\"userTypeId\":" + userTypeId + "}"))
                .andReturn().getResponse().getContentAsString();

        Long userId = ((Number) JsonPath.read(response, "$.id")).longValue();

        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenNotFound() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenEmailIsInvalid() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"João\",\"email\":\"email-invalido\",\"userTypeId\":" + userTypeId + "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"email\":\"joao@email.com\",\"userTypeId\":" + userTypeId + "}"))
                .andExpect(status().isBadRequest());
    }
}
