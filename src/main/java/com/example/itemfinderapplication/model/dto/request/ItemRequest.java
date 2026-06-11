package com.example.itemfinderapplication.model.dto.request;

import com.example.itemfinderapplication.enums.ItemStatus;
import com.example.itemfinderapplication.enums.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequest {

    @NotBlank(message = "Başlıq boş ola bilməz")
    String title;

    @NotNull(message = "Status seçilməlidir")
    ItemStatus itemStatus;

    @NotNull(message = "Əşya növü seçilməlidir")
    ItemType itemType;

    @NotBlank(message = "Təsvir boş ola bilməz")
    @Size(min = 10, max = 1000,
            message = "Təsvir 10-1000 simvol arasında olmalıdır")
    String description;

    @NotNull(message = "Şəhər seçilməlidir")
    Long cityId;
}
