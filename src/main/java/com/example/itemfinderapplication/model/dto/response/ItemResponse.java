package com.example.itemfinderapplication.model.dto.response;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemResponse {

    Long id;
    String tittle;
    ItemStatus itemStatus;
    ItemType itemType;
    String cityName;
    String ownerEmail;
    LocalDateTime createAt;
}
