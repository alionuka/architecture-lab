package edu.kantseryk.project.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/*
  @author Alona
  @project project
  @class ItemRepository
  @version 1.0.0
  @since 19.04.2025 - 13.18
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Document
    public class Item {

        @Id
        private String id;              // Унікальний ідентифікатор
        private String name;           // Назва
        private String code;           // Код
        private String description;    // Опис

    // Конструктор без id
    public Item(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Item item)) return false;

        return getId().equals(item.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
