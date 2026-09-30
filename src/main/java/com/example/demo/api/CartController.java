package com.example.demo.api;

import com.example.demo.Dto.CartDTO;
import com.example.demo.Dto.OrderDTO;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@PreAuthorize("hasAuthority('USER')")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add/{bookId}")
    public ResponseEntity<?> add(@PathVariable Long bookId, Principal principal) {
        cartService.addToCart(principal.getName(), bookId);
        return ResponseEntity.ok("Added");
    }

    @GetMapping
    public ResponseEntity<CartDTO> cart(Principal principal) {
        return ResponseEntity.ok(cartService.getCart(principal.getName()));
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> checkout(Principal principal) {
        return ResponseEntity.ok(cartService.checkout(principal.getName()));
    }
}


