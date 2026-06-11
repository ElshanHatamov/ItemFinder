package com.example.itemfinderapplication.service;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.model.entity.Item;
import com.example.itemfinderapplication.repository.ItemRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchingService {

    ItemRepository itemRepository;
    EmailService emailService;

    public void checkMatchingItems(Item savedItem) {

        ItemStatus oppositeStatus =
                savedItem.getStatus() == ItemStatus.LOST
                        ? ItemStatus.FOUND
                        : ItemStatus.LOST;

        List<Item> matchingItems =
                itemRepository.findByCityIdAndItemTypeAndStatus(
                        savedItem.getCity().getId(),
                        savedItem.getItemType(),
                        oppositeStatus
                );

        for (Item item : matchingItems) {

            if (!item.getUser().getEmail().equals(savedItem.getUser().getEmail())) {
                emailService.sendMatchingItemNotification(
                        item.getUser().getEmail(),
                        // yeni elani qoyan sexs
                        savedItem
                );
            }
        }
    }
}