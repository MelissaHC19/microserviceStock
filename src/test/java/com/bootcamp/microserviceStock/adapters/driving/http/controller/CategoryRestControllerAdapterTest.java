package com.bootcamp.microserviceStock.adapters.driving.http.controller;

import com.bootcamp.microserviceStock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.bootcamp.microserviceStock.domain.api.ICategoryServicePort;
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
class CategoryRestControllerAdapterTest {
    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private ICategoryRequestMapper categoryRequestMapper;

    @InjectMocks
    private CategoryRestControllerAdapter categoryRestControllerAdapter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestControllerAdapter).build();
    }

    @Test
    @DisplayName("Creates a category")
    void createCategory() throws Exception {
        String body = "{\"name\":\"Sports & Outdoors\",\"description\":\"Get gear for your outdoor adventures and fitness routines.\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should handle validation when name field is empty")
    void createCategoryShouldHandleValidationWhenNameIsEmpty() throws Exception {
        String body = "{\"name\":\"\",\"description\":\"Get gear for your outdoor adventures and fitness routines.\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when description field is empty")
    void createCategoryShouldHandleValidationWhenDescriptionIsEmpty() throws Exception {
        String body = "{\"name\":\"Sports & Outdoors\",\"description\":\"\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when name and description fields are empty")
    void createCategoryShouldHandleValidationWhenNameAndDescriptionAreEmpty() throws Exception {
        String body = "{\"name\":\"\",\"description\":\"\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when name field exceeds maximum field size (50)")
    void createCategoryShouldHandleValidationWhenNameExceedsMaxFieldSize() throws Exception {
        String body = "{\"name\":\"Cutting-Edge Electronic Devices and Innovative Home Gadgets Collection\",\"description\":\"Explore cutting-edge devices for home and personal use with our latest tech collection.\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when description field exceeds maximum field size (90)")
    void createCategoryShouldHandleValidationWhenDescriptionExceedsMaxFieldSize() throws Exception {
        String body = "{\"name\":\"Fashion\",\"description\":\"Explore a vast array of clothing, footwear, and accessories designed for every occasion, ensuring you always look your best, no matter the event or season.\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when name and description fields exceeds maximum field size")
    void createCategoryShouldHandleValidationWhenNameAndDescriptionExceedsMaxFieldSize() throws Exception {
        String body = "{\"name\":\"Cutting-Edge Electronic Devices and Innovative Home Gadgets Collection\",\"description\":\"Discover the latest innovations in technology with our curated collection of cutting-edge devices, perfect for enhancing both your home and personal life.\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when name field is empty and description field exceeds maximum field size (90)")
    void createCategoryShouldHandleValidationWhenNameIsEmptyAndDescriptionExceedsMaxFieldSize() throws Exception {
        String body = "{\"name\":\"\",\"description\":\"Discover the latest innovations in technology with our curated collection of cutting-edge devices, perfect for enhancing both your home and personal life.\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle validation when name field exceeds maximum field size (50) and description field is empty")
    void createCategoryShouldHandleValidationWhenNameExceedsMaxFieldSizeAndDescriptionIsEmpty() throws Exception {
        String body = "{\"name\":\"\",\"description\":\"Discover the latest innovations in technology with our curated collection of cutting-edge devices, perfect for enhancing both your home and personal life.\"}";
        MockHttpServletRequestBuilder request = post("/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }
}