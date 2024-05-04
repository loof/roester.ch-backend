package ch.roester.product;

import ch.roester.tag.Tag;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@RequestMapping(ProductController.REQUEST_MAPPING)
@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public static final String REQUEST_MAPPING = "/products";

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of product to delete") @PathVariable("id") Integer id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String searchQuery, @RequestParam(required = false) String tagNames, @RequestParam(defaultValue = "name") String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable paging = PageRequest.of(page, size).withSort(sort);
        Page<ProductResponseDTO> productPages = null;

      /*  if (!StringUtils.isEmpty(searchQuery) && !StringUtils.isEmpty(tagNames)) {
            productPages = productService.findBySearchQueryAndTags(searchQuery, createTagsFromTagNames(tagNames), paging);
        } else */
            if (!StringUtils.isEmpty(searchQuery)) {
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

    /*@GetMapping("/page-query")
    public ResponseEntity<Page<CoffeeDto>> pageQuery(CoffeeDto coffeeDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CoffeeDto> coffeePage = coffeeService.findByCondition(coffeeDto, pageable);
        return ResponseEntity.ok(coffeePage);
    }*/

    @PatchMapping("{id}")
    public ResponseEntity<ProductRequestDTO> update(@RequestBody @Valid ProductRequestDTO productRequestDTO, @PathVariable("id") Integer id) {
        try {
            ProductResponseDTO updatedProduct = productService.update(id, productRequestDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

    }

    private Set<Tag> createTagsFromTagNames(String tagNamesString) {
        String[] tagNames = tagNamesString.split(",");
        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames) {
            Tag tag = new Tag();
            tag.setName(tagName);
            tags.add(tag);
        }
        return tags;
    }




}