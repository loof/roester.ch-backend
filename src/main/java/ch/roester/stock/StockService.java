package ch.roester.stock;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    @Autowired
    public StockService(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    public StockResponseDTO save(StockRequestDTO stockRequestDTO) {
        Stock stock = stockMapper.fromRequestDTO(stockRequestDTO);
        return stockMapper.toResponseDTO(stockRepository.save(stock));
    }

    public StockResponseDTO findById(Integer id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found"));
        return stockMapper.toResponseDTO(stock);
    }

    public List<StockResponseDTO> findAll() {
        List<Stock> stocks = stockRepository.findAll();
        return stockMapper.toResponseDTO(stocks);
    }

    public StockResponseDTO update(Integer id, StockRequestDTO stockRequestDTO) {
        Stock existingStock = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found"));
        Stock updatedStock = stockMapper.fromRequestDTO(stockRequestDTO);
        updatedStock.setId(id);  // Set the ID to the existing stock's ID
        return stockMapper.toResponseDTO(stockRepository.save(updatedStock));
    }

    public void deleteById(Integer id) {
        stockRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Stock not found"));
        stockRepository.deleteById(id);
    }
}
