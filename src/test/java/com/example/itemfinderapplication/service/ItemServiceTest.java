//package com.example.itemfinderapplication.service;
//
//import com.example.itemfinderapplication.exception.NotFoundException;
//import com.example.itemfinderapplication.exception.UnauthorizedActionException;
//import com.example.itemfinderapplication.model.dto.request.ItemUpdateRequest;
//import com.example.itemfinderapplication.model.entity.Item;
//import com.example.itemfinderapplication.model.entity.User;
//import com.example.itemfinderapplication.repository.CityRepository;
//import com.example.itemfinderapplication.repository.ItemRepository;
//import com.example.itemfinderapplication.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ItemServiceTest {
//
//    @Mock
//    ItemRepository itemRepository;
//
//    @Mock
//    CityRepository cityRepository;
//
//    @Mock
//    UserRepository userRepository;
//
//    @Mock
//    FileStorageService fileStorageService;
//
//    @InjectMocks
//    ItemService itemService;
//
//    @Test
//    void getItemById_shouldThrowNotFoundException_whenItemDoesNotExist() {
//        Long itemId = 100L;
//
//        when(itemRepository.findById(itemId))
//                .thenReturn(Optional.empty());
//
//        assertThrows(
//                NotFoundException.class,
//                () -> itemService.getItemById(itemId)
//        );
//    }
//
//    @Test
//    void updateItem_shouldThrowUnauthorizedException_whenUserIsNotOwner() {
//        Long itemId = 1L;
//
//        User owner = new User();
//        owner.setEmail("owner@gmail.com");
//
//        Item item = new Item();
//        item.setUser(owner);
//
//        ItemUpdateRequest request = new ItemUpdateRequest();
//
//        when(itemRepository.findById(itemId))
//                .thenReturn(Optional.of(item));
//
//        assertThrows(
//                UnauthorizedActionException.class,
//                () -> itemService.updateItem(
//                        itemId,
//                        request,
//                        "another@gmail.com"
//                )
//        );
//    }
//}