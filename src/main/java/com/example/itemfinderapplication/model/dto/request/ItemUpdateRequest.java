package com.example.itemfinderapplication.model.dto.request;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemUpdateRequest {

    String title;
    ItemStatus itemStatus;
    ItemType itemType;
    String description;
    Long cityId;
}
