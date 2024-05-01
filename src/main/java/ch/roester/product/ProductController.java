package ch.roester.product;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<ProductResponseDTO> save(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
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
    public ResponseEntity<?> findProducts() {
        return ResponseEntity.ok(productMapper.toResponseDTO(productService.findAll()));
    }

    /*@GetMapping("/page-query")
    public ResponseEntity<Page<CoffeeDto>> pageQuery(CoffeeDto coffeeDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CoffeeDto> coffeePage = coffeeService.findByCondition(coffeeDto, pageable);
        return ResponseEntity.ok(coffeePage);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<ProductRequestDTO> update(@RequestBody @Valid ProductRequestDTO productRequestDTO, @PathVariable("id") Integer id) {
        try {
            Product updatedProduct = productService.update(productMapper.fromRequestDTO(productRequestDTO), id);
            return ResponseEntity.ok(productMapper.toResponseDTO(updatedProduct));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product could not be created");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

    }
}