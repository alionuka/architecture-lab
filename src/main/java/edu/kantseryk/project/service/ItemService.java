package edu.kantseryk.project.service;

import jakarta.annotation.PostConstruct;
import edu.kantseryk.project.model.Item;
import edu.kantseryk.project.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import edu.kantseryk.project.request.ItemCreateRequest;
import edu.kantseryk.project.request.ItemUpdateRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private List<Item> items = new ArrayList<>();
    {
        items.add(new Item("Freddie Mercury", "Queen", "vocal, piano"));
        items.add(new Item("2", "Paul McCartney", "Beatles", "guitar"));
        items.add(new Item("3", "Mick Jagger", "Rolling Stones", "vocal"));
    }

    @PostConstruct
    void init() {
        itemRepository.deleteAll();
        itemRepository.saveAll(items);
    }

    // CRUD - create read update delete

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item getById(String id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item create(ItemCreateRequest request) {
        if (itemRepository.existsByCode(request.code())) {
            return null;
        }
        Item item = mapToItem(request);
        item.setCreateDate(LocalDateTime.now());
        item.setUpdateDate(new ArrayList<>());
        return itemRepository.save(item);
    }

    public Item update(Item item) {
        return itemRepository.save(item);
    }

    public void delById(String id) {
        itemRepository.deleteById(id);
    }

    private Item mapToItem(ItemCreateRequest request) {
        return new Item(request.name(), request.code(), request.description());
    }

    public Item update(ItemUpdateRequest request) {
        if (request.name() == null || request.code() == null || request.id() == null) {
            return null;
        }

        Item itemPersisted = itemRepository.findById(request.id()).orElse(null);
        if (itemPersisted != null) {
            List<LocalDateTime> updateDates = itemPersisted.getUpdateDate();
            if (updateDates == null) {
                updateDates = new ArrayList<>();
            }
            updateDates.add(LocalDateTime.now());

            Item itemToUpdate = Item.builder()
                    .id(request.id())
                    .name(request.name())
                    .code(request.code())
                    .description(request.description())
                    .createDate(itemPersisted.getCreateDate())
                    .updateDate(updateDates)
                    .build();

            return itemRepository.save(itemToUpdate);
        }

        return null;
    }
}
