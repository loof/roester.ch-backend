package ch.roester.stock;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@Slf4j
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<StockResponseDTO> create(@RequestBody @Valid StockRequestDTO stockRequestDTO) {
        try {
            StockResponseDTO createdStock = stockService.save(stockRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock could not be created");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDTO> findById(@Parameter(description = "Id of stock to get") @PathVariable("id") Integer id) {
        StockResponseDTO stock = stockService.findById(id);
        return ResponseEntity.ok(stock);
    }

    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> findAll() {
        List<StockResponseDTO> stocks = stockService.findAll();
        return ResponseEntity.ok(stocks);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StockResponseDTO> update(@RequestBody @Valid StockRequestDTO stockRequestDTO, @PathVariable("id") Integer id) {
        try {
            StockResponseDTO updatedStock = stockService.update(id, stockRequestDTO);
            return ResponseEntity.ok(updatedStock);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Stock could not be updated");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of stock to delete") @PathVariable("id") Integer id) {
        try {
            stockService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock not found");
        }
    }
}
