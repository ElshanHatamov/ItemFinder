package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.model.dto.request.ItemRequest;
import com.example.itemfinderapplication.model.dto.response.ItemResponse;
import com.example.itemfinderapplication.model.entity.City;
import com.example.itemfinderapplication.model.entity.Item;
import com.example.itemfinderapplication.model.entity.User;
import com.example.itemfinderapplication.repository.CityRepository;
import com.example.itemfinderapplication.repository.ItemRepository;
import com.example.itemfinderapplication.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemService {


    ItemRepository itemRepository;
    CityRepository cityRepository;
    UserRepository userRepository;

    @Transactional
    public ItemResponse creatItem(ItemRequest request, String ownerEmail) {

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new RuntimeException(
                        "Şəhər tapılmadı: " + request.getCityId()));

        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException(
                        "Istifadeci tapilmadi"));

        Item item = new Item();
        item.setTittle(request.getTittle());
        item.setStatus(request.getItemStatus());
        item.setItemType(request.getItemType());
        item.setCity(city);
        item.setUser(owner);
        // createAt → @CreationTimestamp avtomatik yazilir
        Item saved = itemRepository.save(item);

        log.info("Yeni əşya yerləşdirildi: id={}, tittle={}, status={}",
                saved.getId(), saved.getTittle(), saved.getStatus());

        return toResponse(saved);
    }

    public List<ItemResponse> getAllLostItems() {
        return itemRepository.findByStatus(ItemStatus.LOST)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ItemResponse> getLostItemsByCity(Long cityId) {
        return itemRepository.findByCityIdAndStatus(cityId, ItemStatus.LOST)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ItemResponse toResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .tittle(item.getTittle())
                .itemStatus(item.getStatus())
                .itemType(item.getItemType())
                .cityName(item.getCity().getName())
                .ownerEmail(item.getUser().getEmail())
                .createAt(item.getCreateAt())
                .build();

    }
}
