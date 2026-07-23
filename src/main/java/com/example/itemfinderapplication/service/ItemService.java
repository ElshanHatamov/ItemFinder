package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import com.example.itemfinderapplication.exception.NotFoundException;
import com.example.itemfinderapplication.exception.UnauthorizedActionException;
import com.example.itemfinderapplication.mapper.ItemMapper;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    ItemMapper itemMapper;
    FileStorageService fileStorageService;
    MatchingService matchingService;

    @Transactional
    public ItemResponse creatItem(ItemRequest request,
                                  MultipartFile image,
                                  String ownerEmail) {

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new NotFoundException(
                        "Şəhər tapılmadı: " + request.getCityId()));

        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new NotFoundException(
                        "Istifadeci tapilmadi"));

        String imageUrl = fileStorageService.saveFile(image);

        Item item = itemMapper.toEntity(
                request,
                city,
                owner,
                imageUrl
        );
        // createAt → @CreationTimestamp avtomatik yazilir
        Item saved = itemRepository.save(item);

        log.info("Yeni əşya yerləşdirildi: id={}, title={}, status={}",
                saved.getId(), saved.getTitle(), saved.getStatus());

        matchingService.checkMatchingItems(saved);
        return itemMapper.toResponse(saved);
    }

    public Page<ItemResponse> getAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable)
                .map(itemMapper::toResponse);
    }

    public List<ItemResponse> getLostItemsByCity(Long cityId) {
        return itemRepository.findByCityIdAndStatus(cityId, ItemStatus.LOST)
                .stream()
                .map(itemMapper::toResponse)
                .collect(Collectors.toList());

    }

    @Transactional
    public ItemResponse updateItem(Long id, ItemUpdateRequest request, String ownerEmail) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Esya Tapilmadi " + id));

        if (!item.getUser().getEmail().equals(ownerEmail)) {
            throw new UnauthorizedActionException("Bu esya sizin deyil: ");
        }

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            item.setTitle(request.getTitle());
        }
        if (request.getItemStatus() != null) {
            item.setStatus(request.getItemStatus());
        }
        if (request.getItemType() != null) {
            item.setItemType(request.getItemType());
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            item.setDescription(request.getDescription());
        }
        if (request.getCityId() != null) {
            City city = cityRepository.findById(request.getCityId())
                    .orElseThrow(() -> new NotFoundException("Seher tapilmadi " + request.getCityId()));
            item.setCity(city);
        }
        Item saved = itemRepository.save(item);
        log.info("Esya yenilendi: id={}, ownerEmail={}", saved.getId(), ownerEmail);
        return itemMapper.toResponse(saved);
    }

    @Transactional
    public void deleteItem(Long id, String ownerEmail) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Esya tapilmadi. ID" + id));

        if (!item.getUser().getEmail().equals(ownerEmail)) {
            throw new UnauthorizedActionException("u esya sizin deyil ");
        }
        itemRepository.delete(item);
        log.info("Esya silindi: id={}, ownerEmail={}", id, ownerEmail);
    }

    public ItemResponse getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mehsul Tapilmadi "));
        return itemMapper.toResponse(item);
    }

    public List<ItemResponse> getItemsByUser(String ownerEmail) {
        return itemRepository.findByUserEmail(ownerEmail)
                .stream()
                .map(itemMapper::toResponse)
                .collect(Collectors.toList());

    }

    // Tapilmis esyalari isareleyir
    @Transactional
    public ItemResponse markAsFound(Long id, String ownerEmail) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mehsul Tapilmadi"));
        if (!item.getUser().getEmail().equals(ownerEmail)) {
            throw new UnauthorizedActionException("Bu esya sizin deyil ");
        }
        item.setStatus(ItemStatus.FOUND);
        Item saved = itemRepository.save(item);
        return itemMapper.toResponse(saved);

    }

    public Page<ItemResponse> searchItems(Long cityId,
                                          ItemType itemType,
                                          ItemStatus itemStatus,
                                          Pageable pageable) {
        return itemRepository.searchItems(cityId, itemType, itemStatus, pageable)
                .map(itemMapper::toResponse);
    }

}
