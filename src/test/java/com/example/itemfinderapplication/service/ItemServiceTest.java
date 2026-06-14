package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import com.example.itemfinderapplication.exception.NotFoundException;
import com.example.itemfinderapplication.model.dto.request.ItemRequest;
import com.example.itemfinderapplication.model.dto.response.ItemResponse;
import com.example.itemfinderapplication.model.entity.City;
import com.example.itemfinderapplication.model.entity.Item;
import com.example.itemfinderapplication.model.entity.User;
import com.example.itemfinderapplication.repository.CityRepository;
import com.example.itemfinderapplication.repository.ItemRepository;
import com.example.itemfinderapplication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;

    @Mock
    CityRepository cityRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    FileStorageService fileStorageService;

    @Mock
    MatchingService matchingService;

    @Mock
    MultipartFile image;

    @InjectMocks
    ItemService itemService;

    @Test
    void shouldCreateItemSuccessfully() {
        ItemRequest request = new ItemRequest();
        request.setTitle("Qara pulqabı");
        request.setItemStatus(ItemStatus.LOST);
        request.setItemType(ItemType.WALLET);
        request.setDescription("Bakı şəhərində qara rəngli pulqabı itmişdir");
        request.setCityId(1L);

        City city = new City();
        city.setId(1L);
        city.setName("Bakı");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPhone("0501234567");

        Item savedItem = new Item();
        savedItem.setId(1L);
        savedItem.setTitle(request.getTitle());
        savedItem.setStatus(request.getItemStatus());
        savedItem.setItemType(request.getItemType());
        savedItem.setDescription(request.getDescription());
        savedItem.setCity(city);
        savedItem.setUser(user);
        savedItem.setImageUrl("/uploads/test-image.png");
        savedItem.setCreateAt(LocalDateTime.now());

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(fileStorageService.saveFile(image)).thenReturn("/uploads/test-image.png");
        when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

        ItemResponse response = itemService.creatItem(
                request,
                image,
                "test@gmail.com"
        );

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Qara pulqabı", response.getTitle());
        assertEquals(ItemStatus.LOST, response.getItemStatus());
        assertEquals(ItemType.WALLET, response.getItemType());
        assertEquals("Bakı", response.getCityName());
        assertEquals("test@gmail.com", response.getOwnerEmail());

        verify(cityRepository).findById(1L);
        verify(userRepository).findByEmail("test@gmail.com");
        verify(fileStorageService).saveFile(image);
        verify(itemRepository).save(any(Item.class));
        verify(matchingService).checkMatchingItems(savedItem);
    }

    @Test
    void shouldThrowExceptionWhenCityNotFound() {
        ItemRequest request = new ItemRequest();
        request.setTitle("Qara pulqabı");
        request.setItemStatus(ItemStatus.LOST);
        request.setItemType(ItemType.WALLET);
        request.setDescription("Bakı şəhərində qara rəngli pulqabı itmişdir");
        request.setCityId(99L);

        when(cityRepository.findById(99L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> itemService.creatItem(request, image, "test@gmail.com")
        );

        assertEquals("Şəhər tapılmadı: 99", exception.getMessage());

        verify(cityRepository).findById(99L);
        verify(userRepository, never()).findByEmail(anyString());
        verify(fileStorageService, never()).saveFile(any());
        verify(itemRepository, never()).save(any());
        verify(matchingService, never()).checkMatchingItems(any());
    }
}