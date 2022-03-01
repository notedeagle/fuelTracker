package com.fuel.tracker.fueltracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldLoginAndGetContent() throws Exception {
        MvcResult login = mvc.perform(post("/login").content("{\"email\": \"test\", \"password\": \"test\"}"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        String token = login.getResponse().getHeader("Authorization");

        mvc.perform(get("/secured").header("Authorization", token))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string("secured"));

        mvc.perform(get("/secured"))
                .andDo(print())
                .andExpect(status().is(401));
    }
}
