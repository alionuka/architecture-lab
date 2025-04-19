package edu.kantseryk.project.controller;
import edu.kantseryk.project.model.Item;
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
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
public class ItemRestController {

    private final ItemService itemService;


        // GET: Повертає всі елементи
        @GetMapping
        public List<Item> showAll() {
            return itemService.getAll();
        }

        // GET: Повертає один елемент по id
        @GetMapping("{id}")
        public Item showOneById(@PathVariable String id) {
            return itemService.getById(id);
        }
        @PostMapping
         public Item insert(@RequestBody Item item) {
            return itemService.create(item);
        }
        @PutMapping
        public Item edit(@RequestBody Item item) {
            return itemService.update(item);
        }
        @DeleteMapping("{id}")
        public void delete(@PathVariable String id) {
            itemService.delById(id);
    }


}