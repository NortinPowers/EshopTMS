package by.tms.eshop.service.impl;

import by.tms.eshop.dto.ProductDto;
import by.tms.eshop.model.Location;
import by.tms.eshop.repository.CartRepository;
import by.tms.eshop.service.CartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

//    @Transactional
//    @Override
//    public void addSelectedProduct(Long userId, Long productId, LocationDto locationDto) {
//        cartRepository.addSelectedProduct(userId, productId, locationDto);
//    }

    @Transactional
    @Override
    public void addSelectedProduct(Long userId, Long productId, Location location) {
        cartRepository.addSelectedProduct(userId, productId, location);
    }

//    @Override
//    public List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, LocationDto locationDto) {
//        return cartRepository.getSelectedProducts(userId, locationDto);
//    }

    @Override
    public List<ImmutablePair<ProductDto, Integer>> getSelectedProducts(Long userId, Location location) {
        return cartRepository.getSelectedProducts(userId, location);
    }

//    @Transactional
//    @Override
//    public void deleteProduct(Long userId, Long productId, LocationDto locationDto) {
//        cartRepository.deleteProduct(userId, productId, locationDto);
//    }

    @Transactional
    @Override
    public void deleteProduct(Long userId, Long productId, Location location) {
        cartRepository.deleteProduct(userId, productId, location);
    }

    @Transactional
    @Override
    public void deleteCartProductsAfterBuy(Long userId) {
//        cartRepository.deleteCartByUserId(userId);
        cartRepository.deleteCartByUserIdAndCart(userId, true);
    }

//    @Override
//    public List<ProductDto> getPurchasedProducts(Long userId, LocationDto locationDto) {
//        return cartRepository.getPurchasedProducts(userId, locationDto);
//    }

    @Override
    public List<ProductDto> getPurchasedProducts(Long userId, Location location) {
        return cartRepository.getPurchasedProducts(userId, location);
    }
}
