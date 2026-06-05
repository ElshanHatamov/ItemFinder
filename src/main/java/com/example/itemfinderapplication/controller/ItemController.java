package com.example.itemfinderapplication.controller;

import com.example.itemfinderapplication.model.dto.request.ItemRequest;
import com.example.itemfinderapplication.model.dto.request.ItemUpdateRequest;
import com.example.itemfinderapplication.model.dto.response.ItemResponse;
import com.example.itemfinderapplication.security.CustomUserDetails;
import com.example.itemfinderapplication.service.ItemService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemController {

    ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(
            @Valid @RequestBody ItemRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemService.creatItem(
                        request,
                        userDetails.getUsername()
                ));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllLostItems() {
        return ResponseEntity.ok(itemService.getAllLostItems());
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<ItemResponse>> getLostItemsByCity(@PathVariable Long cityId) {

        return ResponseEntity.ok(itemService.getLostItemsByCity(cityId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@Valid @PathVariable Long id,
                                                   @RequestBody ItemUpdateRequest updateRequest,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(
                itemService.updateItem(id, updateRequest, userDetails.getUsername()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        itemService.deleteItem(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(
                itemService.getItemById(id)
        );
    }

    @GetMapping("/my-items")
    public ResponseEntity<List<ItemResponse>> getItemsByUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(
                itemService.getItemsByUser(userDetails.getUsername())
        );
    }
}
