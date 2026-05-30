package com.example.itemfinderapplication.model.dto.request;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemUpdateRequest {

    String tittle;
    ItemStatus itemStatus;
    ItemType itemType;
    Long cityId;
}
