package com.example.itemfinderapplication.controller;

import com.example.itemfinderapplication.model.dto.response.ItemResponse;
import com.example.itemfinderapplication.model.dto.response.UserResponse;
import com.example.itemfinderapplication.security.CustomUserDetails;
import com.example.itemfinderapplication.service.AdminService;
import com.example.itemfinderapplication.service.ItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminController {
    AdminService adminService;
    ItemService itemService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        return ResponseEntity.ok(adminService.getAllItems());
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteItem(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(
                adminService.deleteUser(id, userDetails.getUsername()));
    }
}
