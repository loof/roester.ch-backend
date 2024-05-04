package ch.roester.product;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
            return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toResponseDTO(productService.save(productMapper.fromRequestDTO(productRequestDTO))));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@Parameter(description = "Id of product to get") @PathVariable("id") Integer id) {
        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(productMapper.toResponseDTO(product));
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
    public ResponseEntity<?> find(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String name, @RequestParam(required = false) String description, @RequestParam(required = false) String tagNames, @RequestParam(required = false) String priceMin, @RequestParam(required = false) String priceMax) {
        List<ProductResponseDTO> products;
        Pageable paging = PageRequest.of(page, size);
        Page<ProductResponseDTO> productPages = productService.findAll(paging);
        products = productPages.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
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
            Product updatedProduct = productService.update(id, productMapper.fromRequestDTO(productRequestDTO));
            return ResponseEntity.ok(productMapper.toResponseDTO(updatedProduct));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

    }
}