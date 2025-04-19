package edu.kantseryk.project.service;
import jakarta.annotation.PostConstruct;
import edu.kantseryk.project.model.Item;
import edu.kantseryk.project.repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
/*
  @author Alona
  @project project
  @class ItemService
  @version 1.0.0
  @since 19.04.2025 - 13.22
*/
public class ItemService {

    private final ItemRepository itemRepository;

    private List<Item> items = new ArrayList<>();

    {
        items.add(new Item("1", "Mary", "000001", "FrontEnd"));
        items.add(new Item("2", "Anna", "000002", "BackEnd"));
        items.add(new Item("3", "Jack", "000003", "ProjectManager"));
    }
    @PostConstruct
    void init() {
        itemRepository.deleteAll();
        itemRepository.saveAll(items);
    }


    // CRUD — create, read, update, delete

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item getById(String id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item update(Item item) {
        return itemRepository.save(item);
    }

    public void delById(String id) {
        itemRepository.deleteById(id);
    }
}
