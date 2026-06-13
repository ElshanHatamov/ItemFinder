package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.model.dto.response.ItemResponse;
import com.example.itemfinderapplication.model.dto.response.UserResponse;
import com.example.itemfinderapplication.model.entity.Item;
import com.example.itemfinderapplication.model.entity.User;
import com.example.itemfinderapplication.repository.ItemRepository;
import com.example.itemfinderapplication.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminService {
    UserRepository userRepository;
    ItemRepository itemRepository;


    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .surname(user.getSurname())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .createAt(user.getCreateAt().toString())
                        .build())
                .toList();
    }

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(item -> ItemResponse.builder()
                        .id(item.getId())
                        .title(item.getTitle())
                        .itemStatus(item.getStatus())
                        .itemType(item.getItemType())
                        .description(item.getDescription())
                        .cityName(item.getCity().getName())
                        .ownerEmail(item.getUser().getEmail())
                        .ownerPhone(item.getUser().getPhone())
                        .createAt(item.getCreateAt())
                        .build()

                ).toList();
    }

    public String deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Elan tapılmadı"));

        itemRepository.delete(item);
        return "Elan uğurla silindi";
    }

    public String deleteUser(Long id, String adminEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Istifadeci Tapilmadi"));

        if (user.getEmail().equals(adminEmail)) {
            throw new RuntimeException("Admin öz hesabını silə bilməz");
        }
        userRepository.delete(user);
        return "User ugurla silindi ";
    }
}
