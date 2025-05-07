package edu.kantseryk.project.service;
import edu.kantseryk.project.model.Item;
import edu.kantseryk.project.repository.ItemRepository;
import edu.kantseryk.project.request.ItemCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/*
  @author Alona
  @project project
  @class ItemServiceTest
  @version 1.0.0
  @since 07.05.2025 - 21.45
*/

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private ItemService underTest;

    @BeforeEach
    void setUp() {
    }
    //  @AfterEach
    void tearsDown(){
        repository.deleteAll();
    }

    @Test
    void whenInsertNewItem_ThenCreateDateIsPresent() {
        //given
        ItemCreateRequest request = new ItemCreateRequest("Till Lindemman", "Rammstein", "poet");
        LocalDateTime now = LocalDateTime.now();
        // when
        Item createdItem = underTest.create(request);
        // then
        assertNotNull(createdItem);
        assertNotNull(createdItem.getId());
        assertEquals("Till Lindemman", createdItem.getName());
        assertEquals("Rammstein", createdItem.getCode());
        assertEquals("poet", createdItem.getDescription());
        assertNotNull(createdItem.getCreateDate());
        assertSame(LocalDateTime.class, createdItem.getCreateDate().getClass());
        assertTrue(createdItem.getCreateDate().isAfter(now));
        assertNotNull(createdItem.getUpdateDate());
        assertSame(ArrayList.class, createdItem.getUpdateDate().getClass());
        assertTrue(createdItem.getUpdateDate().isEmpty());

    }

    @Test
    void update() {
    }
}