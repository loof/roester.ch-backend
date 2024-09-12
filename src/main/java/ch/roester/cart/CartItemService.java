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

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final EventRepository eventRepository;
    private final VariantRepository variantRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper, EventRepository eventRepository, VariantRepository variantRepository, CartRepository cartRepository) {
        this.cartItemMapper = cartItemMapper;
        this.cartItemRepository = cartItemRepository;
        this.eventRepository = eventRepository;
        this.variantRepository = variantRepository;
        this.cartRepository = cartRepository;
    }

    public Page<CartItemResponseDTO> findAll(Pageable pageable) {
        return cartItemMapper.toResponseDTO(cartItemRepository.findAll(pageable));
    }

    public CartItemResponseDTO findById(Integer id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return cartItemMapper.toResponseDTO(cartItem);
    }


    @Transactional
    public CartItemResponseDTO update(Integer id, CartItemRequestDTO updatingCartItem) {
        CartItem existingCartItem = cartItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Variant variant = variantRepository.findById(updatingCartItem.getVariantId()).orElseThrow(EntityNotFoundException::new);
        double deltaAmount = updatingCartItem.getAmount() - existingCartItem.getAmount();
        Cart cart = cartRepository.findById(updatingCartItem.getCartId()).orElseThrow(EntityNotFoundException::new);
        BigDecimal currentTotal = cart.getTotal();
        cart.setTotal(currentTotal.add(variant.getStockMultiplier().multiply(variant.getProduct().getPricePerUnit()).multiply(BigDecimal.valueOf(deltaAmount))));
        cartRepository.save(cart);
        BeanUtils.copyProperties(updatingCartItem, existingCartItem);
        return cartItemMapper.toResponseDTO(cartItemRepository.save(existingCartItem));
    }

    @Transactional
    public CartItemResponseDTO save(CartItemRequestDTO cartItemRequestDTO) {
        Variant variant = variantRepository.findById(cartItemRequestDTO.getVariantId()).orElseThrow(EntityNotFoundException::new);
        Cart cart = cartRepository.findById(cartItemRequestDTO.getCartId()).orElseThrow(EntityNotFoundException::new);
        if (cart.getTotal() != null) {
            cart.setTotal(cart.getTotal().add(variant.getStockMultiplier().multiply(variant.getProduct().getPricePerUnit()).multiply(BigDecimal.valueOf(cartItemRequestDTO.getAmount())) ));
        } else {
            cart.setTotal(variant.getStockMultiplier().multiply(variant.getProduct().getPricePerUnit()).multiply(BigDecimal.valueOf(cartItemRequestDTO.getAmount())));
        }
        cartRepository.save(cart);
        CartItem cartItem = cartItemMapper.fromRequestDTO(cartItemRequestDTO);
        cartItem.setVariant(variant);
        return cartItemMapper.toResponseDTO(cartItemRepository.save(cartItem));
    }

    @Transactional
    public void deleteById(Integer id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        cartItem.getCart().setTotal(cartItem.getCart().getTotal().subtract(cartItem.getVariant().getStockMultiplier().multiply(cartItem.getVariant().getProduct().getPricePerUnit()).multiply(BigDecimal.valueOf(cartItem.getAmount()))));
        cartItemRepository.delete(cartItem);
        cartItemRepository.deleteById(id);
    }

}