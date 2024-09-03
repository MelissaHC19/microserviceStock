package com.bootcamp.microserviceStock.adapters.driving.http.controller;

import com.bootcamp.microserviceStock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.bootcamp.microserviceStock.domain.api.IBrandServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BrandRestControllerAdapterTest {
    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private IBrandRequestMapper brandRequestMapper;

    @InjectMocks
    private BrandRestControllerAdapter brandRestControllerAdapter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(brandRestControllerAdapter).build();
    }

    @Test
    @DisplayName("Creates a brand")
    void createBrand() throws Exception {
        String body = "{\"name\":\"PureGlow\",\"description\":\"Luxury skincare made from natural ingredients, promoting radiant skin with ethical formulas.\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should handle validation when name field is empty")
    void createBrandShouldHandleValidationWhenNameIsEmpty() throws Exception {
        String body = "{\"name\":\"\",\"description\":\"Luxury skincare made from natural ingredients, promoting radiant skin with ethical formulas.\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when description field is empty")
    void createBrandShouldHandleValidationWhenDescriptionIsEmpty() throws Exception {
        String body = "{\"name\":\"PureGlow\",\"description\":\"\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when name and description fields are empty")
    void createBrandShouldHandleValidationWhenNameAndDescriptionIsEmpty() throws Exception {
        String body = "{\"name\":\"\",\"description\":\"\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when name field exceeds maximum field size (50)")
    void createBrandShouldHandleValidationWhenNameExceedsMaxFieldSize() throws Exception {
        String body = "{\"name\":\"Eco-Conscious Sustainable Home Essentials and Environmentally Friendly Living Solutions\",\"description\":\"Offering eco-friendly home products and essentials that promote sustainable and environmentally responsible living.\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when description field exceeds maximum field size (120)")
    void createBrandShouldHandleValidationWhenDescriptionExceedsMaxFieldSize() throws Exception {
        String body = "{\"name\":\"EcoHaven\",\"description\":\"EcoHaven offers a diverse range of eco-friendly home products designed to enhance your sustainable lifestyle. From biodegradable essentials to energy-efficient solutions, we provide everything you need to live responsibly and reduce your environmental impact.\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when name and description fields exceeds maximum field size")
    void createBrandShouldHandleValidationWhenNameAndDescriptionExceedsMaxFieldSize() throws Exception {
        String body = "{\"name\":\"Eco-Conscious Sustainable Home Essentials and Environmentally Friendly Living Solutions\",\"description\":\"Offering a selection of eco-friendly home products and essentials that inspire sustainable, environmentally responsible living every day.\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when name field is empty and description field exceeds maximum field (120)")
    void createBrandShouldHandleValidationWhenNameIsEmptyAndDescriptionExceedsMaxFieldSize() throws Exception {
        String body = "{\"name\":\"\",\"description\":\"Offering a selection of eco-friendly home products and essentials that inspire sustainable, environmentally responsible living every day.\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when name field exceeds maximum field size (50) and description field is empty")
    void createBrandShouldHandleValidationWhenNameExceedsMaxFieldSizeAndDescriptionIsEmpty() throws Exception {
        String body = "{\"name\":\"Eco-Conscious Sustainable Home Essentials and Environmentally Friendly Living Solutions\",\"description\":\"\"}";
        MockHttpServletRequestBuilder request = post("/brand/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }
}