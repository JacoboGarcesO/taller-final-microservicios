package com.cartservice.controller;

import com.cartservice.model.Cart;
import com.cartservice.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class CartControllerTest {
  private final CartService cartService;
  private final WebTestClient webTestClient;

  CartControllerTest() {
    this.cartService = Mockito.mock(CartService.class);
    this.webTestClient = WebTestClient.bindToController(new CartController(cartService)).build();
  }

  @Test
  void createCart_ShouldCreateCart() {
    // Arrange
    String userEmail = "jacobo@gmail.com";
    Mockito.when(cartService.createCart(Mockito.anyString())).thenReturn(Mono.just(new Cart(1L, userEmail)));

    // Act
    webTestClient
      .post()
      .uri("/api/carts/{userEmail}", userEmail)
//      .bodyValue(new )
      .exchange()
      .expectStatus().isOk()
      .expectBody(Cart.class)
      .value(cart -> {
        assertNotNull(cart);
        assertEquals(1L, cart.getId());
        assertEquals(userEmail, cart.getUserEmail());
        assertNotNull(cart.getCreatedDate());
      });

    // Assert
    Mockito.verify(cartService).createCart(Mockito.anyString());
  }
}