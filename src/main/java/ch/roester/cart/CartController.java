package ch.roester.cart;

import ch.roester.app_user.AppUser;
import ch.roester.app_user.AppUserService;
import ch.roester.event.EventRequestDTO;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;


@RequestMapping(CartController.REQUEST_MAPPING)
@RestController
@Slf4j
public class CartController {

    public static final String REQUEST_MAPPING = "/carts";
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final AppUserService appUserService;

    @Autowired
    public CartController(CartService cartService, AppUserService appUserService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.appUserService = appUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> findById(@Parameter(description = "Id of cart to get") @PathVariable("id") Integer id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            AppUser appuser = appUserService.findByEmail(currentPrincipalName);
            if (!Objects.equals(appuser.getCart().getId(), id)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized");
            }
            CartResponseDTO cartResponseDTO = cartService.findById(id);
            return ResponseEntity.ok(cartResponseDTO);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }

    @PostMapping
    public ResponseEntity<CartResponseDTO> createCart(@RequestBody @Valid CartRequestDTO cartRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cartService.save(cartRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart could not be created");
        }
    }

    @PostMapping("{id}/items")
    public ResponseEntity<CartItemResponseDTO> createCartItem(@RequestBody @Valid CartItemRequestDTO cartItemRequestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.save(cartItemRequestDTO));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart item could not be created");
        }
    }

    @PatchMapping("{id}/items")
    public ResponseEntity<CartItemRequestDTO> update(@Valid @RequestBody CartItemRequestDTO cartItemRequestDTO, @PathVariable("id") Integer id) {
        try {
            CartItemRequestDTO updatedCartItem = cartItemService.update(id, cartItemRequestDTO);
            return ResponseEntity.ok(updatedCartItem);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cart item could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found");
        }
    }


    @PatchMapping("{id}")
    public ResponseEntity<CartRequestDTO> update(@Valid @RequestBody CartRequestDTO cartRequestDTO, @PathVariable("id") Integer id) {
        try {
            CartRequestDTO updatedCart = cartService.update(id, cartRequestDTO);
            return ResponseEntity.ok(updatedCart);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cart could not be updated");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Id of cart to delete") @PathVariable("id") Integer id) {
        try {
            cartService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        }
    }

}