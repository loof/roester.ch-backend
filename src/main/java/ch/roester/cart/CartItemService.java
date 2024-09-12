package ch.roester.cart;

import ch.roester.event.Event;
import ch.roester.event.EventRepository;
import ch.roester.variant.Variant;
import ch.roester.variant.VariantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final EventRepository eventRepository;
    private final VariantRepository variantRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper, EventRepository eventRepository, VariantRepository variantRepository) {
        this.cartItemMapper = cartItemMapper;
        this.cartItemRepository = cartItemRepository;
        this.eventRepository = eventRepository;
        this.variantRepository = variantRepository;
    }

    public Page<CartItemResponseDTO> findAll(Pageable pageable) {
        return cartItemMapper.toResponseDTO(cartItemRepository.findAll(pageable));
    }

    public CartItemResponseDTO findById(Integer id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return cartItemMapper.toResponseDTO(cartItem);
    }


    public CartItemResponseDTO update(Integer id, CartItemRequestDTO updatingCartItem) {
        CartItem existingCartItem = cartItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        BeanUtils.copyProperties(updatingCartItem, existingCartItem);
        return cartItemMapper.toResponseDTO(cartItemRepository.save(existingCartItem));
    }

    public CartItemResponseDTO save(CartItemRequestDTO cartItemRequestDTO) {
        //Event event = eventRepository.findById(cartItemRequestDTO.getEventId()).orElseThrow(EntityNotFoundException::new);
        Variant variant = variantRepository.findById(cartItemRequestDTO.getVariantId()).orElseThrow(EntityNotFoundException::new);
        CartItem cartItem = cartItemMapper.fromRequestDTO(cartItemRequestDTO);
        cartItem.setVariant(variant);
        return cartItemMapper.toResponseDTO(cartItemRepository.save(cartItem));
    }

    public void deleteById(Integer id) {
        cartItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.deleteById(id);
    }

}