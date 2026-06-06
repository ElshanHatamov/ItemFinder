package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import com.example.itemfinderapplication.model.dto.request.ItemRequest;
import com.example.itemfinderapplication.model.dto.request.ItemUpdateRequest;
import com.example.itemfinderapplication.model.dto.response.ItemResponse;
import com.example.itemfinderapplication.model.entity.City;
import com.example.itemfinderapplication.model.entity.Item;
import com.example.itemfinderapplication.model.entity.User;
import com.example.itemfinderapplication.repository.CityRepository;
import com.example.itemfinderapplication.repository.ItemRepository;
import com.example.itemfinderapplication.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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
@Transactional(readOnly = true)
public class ItemService {


    ItemRepository itemRepository;
    CityRepository cityRepository;
    UserRepository userRepository;
    FileStorageService fileStorageService;

    @Transactional
    public ItemResponse creatItem(ItemRequest request,
                                  MultipartFile image,
                                  String ownerEmail) {

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new RuntimeException(
                        "Şəhər tapılmadı: " + request.getCityId()));

        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException(
                        "Istifadeci tapilmadi"));

        String imageUrl = fileStorageService.saveFile(image);

        Item item = new Item();
        item.setTittle(request.getTittle());
        item.setStatus(request.getItemStatus());
        item.setItemType(request.getItemType());
        item.setCity(city);
        item.setUser(owner);
        item.setImageUrl(imageUrl);
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
                .imageUrl(item.getImageUrl())
                .createAt(item.getCreateAt())
                .build();

    }

    @Transactional
    public ItemResponse updateItem(Long id, ItemUpdateRequest request, String ownerEmail) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Esya Tapilmadi " + id));

        if (!item.getUser().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("Bu esya sizin deyil: ");
        }

        if (request.getTittle() != null && !request.getTittle().isBlank()) {
            item.setTittle(request.getTittle());
        }
        if (request.getItemStatus() != null) {
            item.setStatus(request.getItemStatus());
        }
        if (request.getItemType() != null) {
            item.setItemType(request.getItemType());
        }
        if (request.getCityId() != null) {
            City city = cityRepository.findById(request.getCityId())
                    .orElseThrow(() -> new RuntimeException("Seher tapilmadi " + request.getCityId()));
            item.setCity(city);
        }
        Item saved = itemRepository.save(item);
        log.info("Esya yenilendi: id={}, ownerEmail={}", saved.getId(), ownerEmail);
        return toResponse(saved);
    }

    @Transactional
    public void deleteItem(Long id, String ownerEmail) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Esya tapilmadi. ID" + id));

        if (!item.getUser().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("User Tapilmadi ");
        }
        itemRepository.delete(item);
        log.info("Esya silindi: id={}, ownerEmail={}", id, ownerEmail);
    }

    public ItemResponse getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mehsul Tapilmadi "));
        return toResponse(item);
    }

    public List<ItemResponse> getItemsByUser(String ownerEmail) {
        return itemRepository.findByUserEmail(ownerEmail)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }

    // Tapilmis esyalari isareleyir
    @Transactional
    public ItemResponse markAsFound(Long id, String ownerEmail) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mehsul Tapilmadi"));
        if (!item.getUser().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("Bu esya sizin deyil ");
        }
        item.setStatus(ItemStatus.FOUND);
        Item saved = itemRepository.save(item);
        return toResponse(saved);

    }

    public List<ItemResponse> searchItems(Long cityId,
                                          ItemType itemType,
                                          ItemStatus itemStatus) {
        return itemRepository.searchItems(cityId, itemType, itemStatus)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }
}
