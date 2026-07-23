package com.example.itemfinderapplication.mapper;

import com.example.itemfinderapplication.model.dto.request.ItemRequest;
import com.example.itemfinderapplication.model.dto.response.ItemResponse;
import com.example.itemfinderapplication.model.entity.City;
import com.example.itemfinderapplication.model.entity.Item;
import com.example.itemfinderapplication.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toEntity(ItemRequest request,
                         City city,
                         User owner,
                         String imageUrl) {
        Item item = new Item();

        item.setTitle(request.getTitle());
        item.setStatus(request.getItemStatus());
        item.setItemType(request.getItemType());
        item.setDescription(request.getDescription());
        item.setCity(city);
        item.setUser(owner);
        item.setImageUrl(imageUrl);

        return item;
    }

    public ItemResponse toResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .title(item.getTitle())
                .itemStatus(item.getStatus())
                .itemType(item.getItemType())
                .description(item.getDescription())
                .cityName(item.getCity().getName())
                .ownerEmail(item.getUser().getEmail())
                .ownerPhone(item.getUser().getPhone())
                .imageUrl(item.getImageUrl())
                .createAt(item.getCreateAt())
                .build();
    }
}
