package com.acme.catalog.backend.controller;

import com.acme.catalog.backend.dto.ProductDto;
import com.acme.catalog.backend.exception.AppException;
import com.acme.catalog.backend.service.JwtService;
import com.acme.catalog.backend.service.ProductService;
import com.acme.catalog.backend.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.List;
import org.junit.runner.RunWith;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @WithMockUser
    @Test
    void whenGetAllProducts_shouldReturnProductList() throws Exception {
        // Given
        ProductDto product1 = ProductDto.builder().id(1L).name("Product 1").description("Description 1").build();
        ProductDto product2 = ProductDto.builder().id(2L).name("Product 2").description("Description 2").build();
        List<ProductDto> productList = List.of(product1, product2);

        given(productService.getAllProducts()).willReturn(productList);

        // When & Then
        mockMvc.perform(get("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Product 1")))
                .andExpect(jsonPath("$[1].name", is("Product 2")));
    }

    @WithMockUser
    @Test
    void whenGetProductById_shouldReturnProduct() throws Exception {
        // Given
        ProductDto product = ProductDto.builder().id(1L).name("Product 1").description("Description 1").build();
        given(productService.getProductById(anyLong())).willReturn(product);

        // When & Then
        mockMvc.perform(get("/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Product 1")))
                .andExpect(jsonPath("$.description", is("Description 1")));
    }

    @WithMockUser
    @Test
    void whenCreateProduct_shouldReturnCreatedProduct() throws Exception {
        // Given
        ProductDto product = ProductDto.builder().id(1L).name("Product 1").description("Description 1").build();
        given(productService.createProduct(any(ProductDto.class))).willReturn(product);

        // When & Then
        mockMvc.perform(post("/product")
               .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Product 1\",\"description\":\"Description 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Product 1")))
                .andExpect(jsonPath("$.description", is("Description 1")));
    }

    @WithMockUser
    @Test
    void whenUpdateProduct_shouldReturnUpdatedProduct() throws Exception {
        // Given
        ProductDto product = ProductDto.builder().id(1L).name("Updated Product").description("Updated Description").build();
        given(productService.updateProduct(anyLong(), any(ProductDto.class))).willReturn(product);

        // When & Then
        mockMvc.perform(put("/product/1")
                 .with(SecurityMockMvcRequestPostProcessors.csrf())
                 .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Product\",\"description\":\"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Product")))
                .andExpect(jsonPath("$.description", is("Updated Description")));
    }

    @WithMockUser
    @Test
    void whenUpdateNonExistentProduct_shouldReturnNotFound() throws Exception {
        // Given
        given(productService.updateProduct(anyLong(), any(ProductDto.class)))
                .willThrow(new AppException("Product not found with id 999", HttpStatus.NOT_FOUND));

        // When & Then
        mockMvc.perform(put("/product/999")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Non-existent Product\",\"description\":\"Non-existent Description\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Product not found with id 999")));
    }

    @WithMockUser
    @Test
    void whenDeleteProduct_shouldReturnDeletedProduct() throws Exception {
        // Given
        ProductDto product = ProductDto.builder().id(1L).name("Product 1").description("Description 1").build();
        given(productService.deleteProduct(anyLong())).willReturn(product);

        // When & Then
        mockMvc.perform(delete("/product/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Product 1")))
                .andExpect(jsonPath("$.description", is("Description 1")));
    }

    @WithMockUser
    @Test
    void whenDeleteNonExistentProduct_shouldReturnNotFound() throws Exception {
        // Given
        given(productService.deleteProduct(anyLong())).willThrow(new AppException("Product not found with id 999", HttpStatus.NOT_FOUND));

        // When & Then
        mockMvc.perform(delete("/product/999")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Product not found with id 999")));
    }
}
