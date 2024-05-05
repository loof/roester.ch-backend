package ch.roester.product;

import ch.roester.utils.TestDataUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;


    @Test
    public void checkPost_whenValidNewProduct_thenIsCreated() throws Exception {
        Mockito.when(productService.save(any(ProductRequestDTO.class))).thenReturn(TestDataUtil.getTestProductDTO());
        Mockito.when(productMapper.toResponseDTO(any(Product.class))).thenReturn(TestDataUtil.getTestProductDTO());
        Mockito.when(productMapper.fromRequestDTO(any(ProductRequestDTO.class))).thenReturn(TestDataUtil.getTestProduct());

        mockMvc.perform(post(ProductController.REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"ProductDTO1\", \"appUserId\":\"1\"}"))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("ProductDTO1")));
    }

    @Test
    public void checkPost_whenInvalidIProduct_thenIsBadRequest() throws Exception {
        Mockito.when(productService.save(any(ProductRequestDTO.class))).thenThrow(DataIntegrityViolationException.class);
        Mockito.when(productMapper.toResponseDTO(any(Product.class))).thenReturn(TestDataUtil.getTestProductDTO());
        Mockito.when(productMapper.fromRequestDTO(any(ProductRequestDTO.class))).thenReturn(TestDataUtil.getTestProduct());

        mockMvc.perform(post(ProductController.REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"wrongFieldName\":\"Product\"}"))

                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkGetAll_whenWithoutParams_thenAllAreReturned() throws Exception {
        Mockito.when(productService.findAll(any(Pageable.class))).thenReturn(TestDataUtil.getTestProductsDTO());

        mockMvc.perform(get(ProductController.REQUEST_MAPPING))
                .andExpect(status().isOk())
                .andExpect(content().json(TestDataUtil.JSON_ALL_PRODUCTS_DTOS));

    }

    @Test
    public void checkFindById_whenInvalidId_thenIsNotFound() throws Exception {
        Mockito.when(productService.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get(ProductController.REQUEST_MAPPING + "/" + 0))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkFindById_whenValidId_thenEventIsReturned() throws Exception {
        ProductResponseDTO expected = TestDataUtil.getTestProductDTO();
        Mockito.when(productService.findById(eq(1))).thenReturn(expected);

        mockMvc.perform(get(ProductController.REQUEST_MAPPING + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("ProductDTO1")));
    }



    @Test
    public void checkGetAll_whenSearchQueryGiven_thenFilteredProductsAreReturned() throws Exception {
        Mockito.when(productService.findBySearchQuery(eq("Product"), any(Pageable.class))).thenReturn(TestDataUtil.getTestProductsDTO());

        mockMvc.perform(get(ProductController.REQUEST_MAPPING + "?searchQuery=Product"))
                .andExpect(status().isOk())
                .andExpect(content().json(TestDataUtil.JSON_ALL_PRODUCTS_DTOS));

    }

    @Test
    public void checkGetAll_whenTagsGiven_thenFilteredProductsAreReturned() throws Exception {
        Mockito.when(productService.findByTags(any() , any(Pageable.class))).thenReturn(TestDataUtil.getTestProductsDTO());

        mockMvc.perform(get(ProductController.REQUEST_MAPPING + "?tagNames=tag1,tag2"))
                .andExpect(status().isOk())
                .andExpect(content().json(TestDataUtil.JSON_ALL_PRODUCTS_DTOS));

    }

    @Test
    public void checkGetAll_whensearchQueryAnsTagsGiven_thenFilteredProductsAreReturned() throws Exception {
        Mockito.when(productService.findBySearchQueryAndTags(any(), any() , any(Pageable.class))).thenReturn(TestDataUtil.getTestProductsDTO());

        mockMvc.perform(get(ProductController.REQUEST_MAPPING + "?tagNames=tag1,tag2&searchQuery=Product"))
                .andExpect(status().isOk())
                .andExpect(content().json(TestDataUtil.JSON_ALL_PRODUCTS_DTOS));

    }

    @Test
    public void checkDelete_whenValidId_thenIsNoContent() throws Exception {
        mockMvc.perform(delete(ProductController.REQUEST_MAPPING + "/" + 1))
                .andExpect(status().isNoContent());
    }

    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        Mockito.doThrow(EmptyResultDataAccessException.class).when(productService).deleteById(0);

        mockMvc.perform(delete(ProductController.REQUEST_MAPPING + "/" + 0))
                .andExpect(status().isNotFound());
    }

}