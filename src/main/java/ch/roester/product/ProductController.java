package ch.roester.product;

import ch.roester.tag.TagService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RequestMapping(ProductController.REQUEST_MAPPING)
@RestController
@Slf4j
public class ProductController {

    public static final String REQUEST_MAPPING = "/products";
    private final ProductService productService;
    private final TagService tagService;

    @Autowired
    public ProductController(ProductService productService, TagService tagService) {
        this.productService = productService;
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@Parameter(description = "Id of product to get") @PathVariable("id") Integer id) {
        try {
            ProductResponseDTO product = productService.findById(id);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String searchQuery, @RequestParam(required = false) String tagNames, @RequestParam(defaultValue = "name") String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable paging = PageRequest.of(page, size).withSort(sort);
        Page<ProductResponseDTO> productPages = null;

        if (!StringUtils.isEmpty(searchQuery) && !StringUtils.isEmpty(tagNames)) {
            productPages = productService.findBySearchQueryAndTags(searchQuery, Arrays.asList(tagNames.split(",")), paging);
        } else if (!StringUtils.isEmpty(searchQuery)) {
            productPages = productService.findBySearchQuery(searchQuery, paging);
        } else if (!StringUtils.isEmpty(tagNames)) {
            productPages = productService.findByTags(Arrays.asList(tagNames.split(",")), paging);
        } else {
            productPages = productService.findAll(paging);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("products", productPages.getContent());
        response.put("currentPage", productPages.getNumber());
        response.put("totalItems", productPages.getTotalElements());
        response.put("totalPages", productPages.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @PatchMapping("{id}")
    public ResponseEntity<ProductRequestDTO> update(@RequestBody ProductRequestDTO productRequestDTO, @PathVariable("id") Integer id) {
        try {
            ProductResponseDTO updatedProduct = productService.update(id, productRequestDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of product to delete") @PathVariable("id") Integer id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

}