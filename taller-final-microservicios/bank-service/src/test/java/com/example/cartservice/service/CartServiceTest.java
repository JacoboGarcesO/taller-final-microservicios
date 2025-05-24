package com.cartservice.service;

import com.cartservice.dto.AddProductDTO;
import com.cartservice.dto.CartDTO;
import com.cartservice.dto.GetProductDTO;
import com.cartservice.event.CartEventPublisher;
import com.cartservice.model.Cart;
import com.cartservice.model.Product;
import com.cartservice.repository.ICartRepository;
import com.cartservice.repository.IProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {
  private final ICartRepository cartRepository;
  private final IProductRepository productRepository;
  private final GetProductClient getProductClient;
  private final CartEventPublisher cartEventPublisher;
  private final CartService cartService;

  public CartServiceTest() {
    cartRepository = Mockito.mock(ICartRepository.class);
    productRepository = Mockito.mock(IProductRepository.class);
    getProductClient = Mockito.mock(GetProductClient.class);
    cartEventPublisher = Mockito.mock(CartEventPublisher.class);
    cartService = new CartService(cartRepository, productRepository, getProductClient, cartEventPublisher);
  }

  @Test
  void createCart_ShouldCreateAndReturnCart() {
    // Arrange
    String userEmail = "jacobo@gmail.com";
    Mockito.when(cartRepository.save(Mockito.any(Cart.class)))
      .thenReturn(Mono.just(new Cart(1L, userEmail)));
    // Act
    StepVerifier
      .create(cartService.createCart(userEmail))
      .assertNext(cart -> {
        assertNotNull(cart);
        assertEquals(1L, cart.getId());
        assertEquals(userEmail, cart.getUserEmail());
      })
      .verifyComplete();
    // Assert
    Mockito.verify(cartRepository).save(Mockito.any(Cart.class));
  }

  @Test
  void addProduct_WithEnoughStock_ShouldReturnUpdatedCart() {
    // Arrange
    String userEmail = "jacobo@gmail.com";
    AddProductDTO addProductDTO = new AddProductDTO(1L, 2);
    Mockito.when(getProductClient.getProduct(Mockito.anyLong())).thenReturn(Mono.just(new GetProductDTO(1L, "Product 1", "Description 1", 10)));
    Mockito.when(cartRepository.findByUserEmail(Mockito.anyString())).thenReturn(Mono.just(new Cart(1L, userEmail)));
    Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(Mono.just(new Product(1L, "Product 1", "Description 1", 2, 1L)));
    Mockito.when(productRepository.findAllByCartId(Mockito.anyLong())).thenReturn(Flux.just(new Product(1L, "Product 1", "Description 1", 2, 1L)));
    // Act
    StepVerifier.create(cartService.addProduct(addProductDTO, userEmail))
      .expectNextMatches(cart -> cart.getProducts().size() == 1 && cart.getProducts().get(0).getQuantity() == 2)
      .verifyComplete();
    // Assert
    Mockito.verify(getProductClient).getProduct(Mockito.anyLong());
    Mockito.verify(cartRepository).findByUserEmail(Mockito.anyString());
    Mockito.verify(productRepository).save(Mockito.any(Product.class));
    Mockito.verify(productRepository).findAllByCartId(Mockito.anyLong());
  }

  @Test
  void addProduct_WithNotEnoughStock_ShouldReturnUpdatedCart() {
    // Arrange
    String userEmail = "jacobo@gmail.com";
    AddProductDTO addProductDTO = new AddProductDTO(1L, 2);
    Mockito.when(getProductClient.getProduct(Mockito.anyLong())).thenReturn(Mono.just(new GetProductDTO(1L, "Product 1", "Description 1", 1)));

    // Act
    StepVerifier.create(cartService.addProduct(addProductDTO, userEmail))
      .expectError(RuntimeException.class);
    // Assert
    Mockito.verify(getProductClient).getProduct(Mockito.anyLong());
  }
}