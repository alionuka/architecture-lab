package edu.kantseryk.project.controller;
import edu.kantseryk.project.model.Item;
import edu.kantseryk.project.request.ItemCreateRequest;
import edu.kantseryk.project.request.ItemUpdateRequest;
import edu.kantseryk.project.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
  @author Alona
  @project project
  @class ItemRestController
  @version 1.0.0
  @since 19.04.2025 - 15.07
*/
@RestController
@RequestMapping("api/v1/items/")
@RequiredArgsConstructor
public class ItemRestController {

    private final ItemService itemService;
    //  private final ItemRepository repository;


    // CRUD   create read update delete

    // read all
    @GetMapping
    public List<Item> showAll() {
        return itemService.getAll();
    }

    // read one
    @GetMapping("{id}")
    public Item showOneById(@PathVariable String id) {
        return itemService.getById(id);
    }

    @PostMapping
    public Item insert(@RequestBody Item item) {
        return itemService.create(item);
    }

    //============== request =====================
    @PostMapping("/dto")
    public Item insert(@RequestBody ItemCreateRequest request) {
        return itemService.create(request);
    }

    @PutMapping
    public Item edit(@RequestBody Item item) {
        return itemService.update(item);
    }
    //============== request =====================
    @PutMapping("/dto")
    public Item edit(@RequestBody ItemUpdateRequest request) {
        return itemService.update(request);
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        itemService.delById(id);
    }

}