package ch.roester.cart;

import ch.roester.event.Event;
import ch.roester.event.EventRepository;
import ch.roester.event_product_amount.EventProductAmount;
import ch.roester.event_product_amount.EventProductAmountRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final EventRepository eventRepository;
    private final VariantRepository variantRepository;
    private final CartRepository cartRepository;
    private final EventProductAmountRepository eventProductAmountRepository;
    private static final Set<Integer> eventProductAmountIds = new HashSet<>();
    private static final Set<Integer> variantIds = new HashSet<>();
    private static final HashMap<Integer, EventProductAmount> eventProductAmountsMap = new HashMap<>();
    private static final HashMap<Integer, Variant> variantsMap = new HashMap<>();

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper, EventRepository eventRepository, VariantRepository variantRepository, CartRepository cartRepository, EventProductAmountRepository eventProductAmountRepository) {
        this.cartItemMapper = cartItemMapper;
        this.cartItemRepository = cartItemRepository;
        this.eventRepository = eventRepository;
        this.variantRepository = variantRepository;
        this.cartRepository = cartRepository;
        this.eventProductAmountRepository = eventProductAmountRepository;
    }

    public Page<CartItemResponseDTO> findAll(Pageable pageable) {
        return cartItemMapper.toResponseDTO(cartItemRepository.findAll(pageable));
    }

    public CartItemResponseDTO findById(Integer id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return cartItemMapper.toResponseDTO(cartItem);
    }


    @Transactional
    public CartItemResponseDTO update(Integer itemId, Integer cartId, CartItemRequestDTO updatingCartItem) {
        updatingCartItem.setId(itemId);
        CartItem existingCartItem = cartItemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        Variant variant = variantRepository.findById(updatingCartItem.getVariantId()).orElseThrow(EntityNotFoundException::new);
        double deltaAmount = updatingCartItem.getAmount() - existingCartItem.getAmount();
        Cart cart = cartRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);
        cartRepository.save(cart);
        BeanUtils.copyProperties(updatingCartItem, existingCartItem);
        return cartItemMapper.toResponseDTO(cartItemRepository.save(existingCartItem));
    }

    private void prepareVariantsCache(CartItemRequestDTO[] cartItems) {
        // Loop through the array of CartItemRequestDTO and add ids to cache
        for (CartItemRequestDTO item : cartItems) {
            variantIds.add(item.getVariantId());
        }

        List<Variant> variants = variantRepository.findAllById(variantIds);

        for (Variant variant : variants) {
            variantsMap.put(variant.getId(), variant);
        }
    }

    private void prepareEventProductAmountCache(CartItemRequestDTO[] cartItems) {
        // Loop through the array of CartItemRequestDTO and add ids to cache
        for (CartItemRequestDTO item : cartItems) {
            eventProductAmountIds.add(item.getEventProductAmountId());
        }

        List<EventProductAmount> eventProductAmounts = eventProductAmountRepository.findAllById(eventProductAmountIds);

        for (EventProductAmount eventProductAmount : eventProductAmounts) {
            eventProductAmountsMap.put(eventProductAmount.getId(), eventProductAmount);
        }
    }

    private BigDecimal getSubTotalForEventProduct(EventProductAmount epa, Set<CartItem> cartItems) {
        if (epa == null) {
            return new BigDecimal(0);
        }
        BigDecimal subTotal = new BigDecimal(0);
        for (CartItem item : cartItems) {
            BigDecimal stockMultiplier = variantsMap.get(item.getVariant().getId()).getStockMultiplier();
            if (Objects.equals(item.getEventProductAmount().getId(), epa.getId())) {
                subTotal = subTotal.add(stockMultiplier.multiply(BigDecimal.valueOf(item.getAmount())));
            }
        }

        return subTotal;
    }

    private CartItemRequestDTO[] mergeCartItems(CartItemRequestDTO[] cartItems) {

        // Map to store merged CartItemRequestDTOs with summed amounts
        Map<CartItemRequestDTO, Double> mergedItemsMap = new HashMap<>();

        for (CartItemRequestDTO item : cartItems) {
            mergedItemsMap.merge(item, item.getAmount(), Double::sum);
        }

        // Convert the map entries into CartItemRequestDTO objects with updated amounts
        List<CartItemRequestDTO> mergedList = new ArrayList<>();
        for (Map.Entry<CartItemRequestDTO, Double> entry : mergedItemsMap.entrySet()) {
            CartItemRequestDTO mergedItem = entry.getKey();
            mergedItem.setAmount(entry.getValue()); // Update the amount
            mergedList.add(mergedItem);
        }

        CartItemRequestDTO[] result = new CartItemRequestDTO[mergedList.size()];

        return mergedList.toArray(result);

    }

    @Transactional
    public CartItemResponseDTO[] saveAll(Integer cartId, CartItemRequestDTO[] cartItemRequestDTOS) {
        prepareVariantsCache(cartItemRequestDTOS);
        prepareEventProductAmountCache(cartItemRequestDTOS);

        cartItemRequestDTOS = mergeCartItems(cartItemRequestDTOS);
        CartItemResponseDTO[] responseDTOs = new CartItemResponseDTO[cartItemRequestDTOS.length];
        for (int i = 0; i < cartItemRequestDTOS.length; i++) {
            responseDTOs[i] = save(cartId, cartItemRequestDTOS[i]);
        }
        return responseDTOs;
    }

    @Transactional
    public CartItemResponseDTO save(Integer cartId, CartItemRequestDTO cartItemRequestDTO) {

        Variant variant = variantRepository.findById(cartItemRequestDTO.getVariantId()).orElseThrow(EntityNotFoundException::new);
        Cart cart = cartRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);

        EventProductAmount eventProductAmount = null;
        if (cartItemRequestDTO.getEventProductAmountId() != null) {
            eventProductAmount = eventProductAmountRepository.findById(cartItemRequestDTO.getEventProductAmountId()).orElseThrow(EntityNotFoundException::new);
        }

        Set<CartItem> existingCartItems = cart.getItems();
        CartItem cartItemToSave = null;

        // Check if there is an existing cart item (variant and optionally event are the same).
        for (CartItem existingCartItem : existingCartItems) {
            if (Objects.equals(existingCartItem.getVariant().getId(), cartItemRequestDTO.getVariantId())) {
                if ((existingCartItem.getEventProductAmount() == null && cartItemRequestDTO.getEventProductAmountId() == null) || Objects.equals(Objects.requireNonNull(existingCartItem.getEventProductAmount()).getId(), cartItemRequestDTO.getEventProductAmountId())) {
                    cartItemToSave = existingCartItem;
                }
            }
        }

        // If no existing cart item found, create entity object to save
        if (cartItemToSave == null) {

            cartItemToSave = cartItemMapper.fromRequestDTO(cartItemRequestDTO);
            cartItemToSave.setVariant(variant);
            cartItemToSave.setCart(cart);

        } else {

            BigDecimal amount = BigDecimal.valueOf(cartItemRequestDTO.getAmount());
            BigDecimal stockMultiplier = variantsMap.get(cartItemToSave.getVariant().getId()).getStockMultiplier(); // Assuming it's already BigDecimal
            BigDecimal subTotal = getSubTotalForEventProduct(eventProductAmountsMap.get(cartItemToSave.getEventProductAmount().getId()), cart.getItems()); // Assuming this returns BigDecimal
            BigDecimal amountLeft = BigDecimal.valueOf(eventProductAmountsMap.get(cartItemToSave.getEventProductAmount().getId()).getAmountLeft());
            if (amount.multiply(stockMultiplier).add(subTotal).compareTo(amountLeft) <= 0) {
                // If the item already exists in the map, sum up the amount
                cartItemToSave.setAmount(cartItemToSave.getAmount() + cartItemRequestDTO.getAmount());
            }

        }

        return cartItemMapper.toResponseDTO(cartItemRepository.save(cartItemToSave));
    }

    @Transactional
    public void deleteById(Integer id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
        cartItemRepository.deleteById(id);
    }

}