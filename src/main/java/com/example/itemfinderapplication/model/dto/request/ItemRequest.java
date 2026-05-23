package com.example.itemfinderapplication.model.dto.request;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequest {

    @NotBlank(message = "Başlıq boş ola bilməz")
    String tittle;

    @NotNull(message = "Status seçilməlidir")
    ItemStatus itemStatus;

    @NotNull(message = "Əşya növü seçilməlidir")
    ItemType itemType;

    @NotNull(message = "Şəhər seçilməlidir")
    Long cityId;
}
