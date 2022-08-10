package com.testlab2.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CryptoService cpservice;

    @WithMockUser(username = "fakeuser", authorities = "admin")
    @Test
    void shouldGreetDefault() throws Exception {
        mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello Oscar")));
    }

    @WithMockUser(username = "fakeuser", authorities = "admin")
    @Test
    void shouldGreetByName() throws Exception {
        String greetingName = "Ferguson";
        mockMvc.perform(get("/hello").param("targetName", greetingName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello Ferguson")));
    }

    @WithMockUser(username = "fakeuser", authorities = "admin")
    @ParameterizedTest
    @ValueSource(strings = {"bitcoin", "ethereum", "cardano"})
    void getCoinPrice(String crypto) throws Exception {
        mockMvc.perform(get("/getCryptoPrice/" + crypto))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("The Price of " + crypto + " is: " + cpservice.getCoinPrice(crypto))));
    }
}