package ch.roester.cart;

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
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Autowired
    public CartService(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartMapper = cartMapper;
        this.cartRepository = cartRepository;
    }

    public Page<CartResponseDTO> findAll(Pageable pageable) {
        return cartMapper.toResponseDTO(cartRepository.findAll(pageable));
    }

    public CartResponseDTO findById(Integer id) {
        Cart cart = cartRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return cartMapper.toResponseDTO(cart);
    }


    public CartResponseDTO update(Integer id, CartRequestDTO updatingCart) {
        Optional<Cart> existingCart = cartRepository.findById(id);
        if (existingCart.isEmpty()) {
            throw new EntityNotFoundException();
        }
        BeanUtils.copyProperties(updatingCart, existingCart);
        return cartMapper.toResponseDTO(cartRepository.save(existingCart.get()));
    }

    public CartResponseDTO save(CartRequestDTO cart) {
        return cartMapper.toResponseDTO(cartRepository.save(cartMapper.fromRequestDTO(cart)));
    }

    public void deleteById(Integer id) {
        cartRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        cartRepository.deleteById(id);
    }

}