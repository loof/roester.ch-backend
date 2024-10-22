package ch.roester.shipment;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;

    @Autowired
    public ShipmentService(ShipmentRepository shipmentRepository, ShipmentMapper shipmentMapper) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentMapper = shipmentMapper;
    }

    public Page<ShipmentResponseDTO> findAll(Pageable pageable) {
        return shipmentMapper.toResponseDTO(shipmentRepository.findAll(pageable));
    }

    public ShipmentResponseDTO findById(Integer id) {
        return shipmentMapper.toResponseDTO(shipmentRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public ShipmentResponseDTO update(Integer id, ShipmentRequestDTO updatingShipment) {
        Optional<Shipment> existingShipment = shipmentRepository.findById(id);
        if (existingShipment.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Shipment shipmentToUpdate = existingShipment.get();
        // Assuming that you want to update only the properties of the existing shipment
        //shipmentMapper.updateFromRequestDTO(updatingShipment, shipmentToUpdate);
        return shipmentMapper.toResponseDTO(shipmentRepository.save(shipmentToUpdate));
    }

    public ShipmentResponseDTO save(ShipmentRequestDTO shipment) {
        return shipmentMapper.toResponseDTO(shipmentRepository.save(shipmentMapper.fromRequestDTO(shipment)));
    }

    public void deleteById(Integer id) {
        shipmentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        shipmentRepository.deleteById(id);
    }
}
