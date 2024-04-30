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
    private final ProductService coffeeService;
    private final ProductMapper coffeeMapper;

    public static final String REQUEST_MAPPING = "/coffees";

    @Autowired
    public ProductController(ProductService coffeeService, ProductMapper coffeeMapper) {
        this.coffeeService = coffeeService;
        this.coffeeMapper = coffeeMapper;
    }

    @PostMapping
    public ResponseEntity<ProductRequestDTO> save(@RequestBody @Valid ProductRequestDTO coffeeDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(coffeeMapper.toDto(coffeeService.save(coffeeMapper.toEntity(coffeeDto))));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coffee could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRequestDTO> findById(@Parameter(description = "Id of coffee to get") @PathVariable("id") Integer id) {
        try {
            Product coffee = coffeeService.findById(id);
            return ResponseEntity.ok(coffeeMapper.toDto(coffee));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coffee not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of item to delete") @PathVariable("id") Integer id) {
        try {
            coffeeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coffee not found");
        }
    }

    @GetMapping
    public ResponseEntity<?> findCoffees() {
        return ResponseEntity.ok(coffeeMapper.toDto(coffeeService.findAll()));
    }

    /*@GetMapping("/page-query")
    public ResponseEntity<Page<CoffeeDto>> pageQuery(CoffeeDto coffeeDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CoffeeDto> coffeePage = coffeeService.findByCondition(coffeeDto, pageable);
        return ResponseEntity.ok(coffeePage);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<ProductRequestDTO> update(@RequestBody @Valid ProductRequestDTO coffeeDto, @PathVariable("id") Integer id) {
        try {
            Product updatedCoffee = coffeeService.update(coffeeMapper.toEntity(coffeeDto), id);
            return ResponseEntity.ok(coffeeMapper.toDto(updatedCoffee));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Coffee could not be created");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coffee not found");
        }

    }
}