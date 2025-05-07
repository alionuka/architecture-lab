package edu.kantseryk.project;
import edu.kantseryk.project.model.Item;
import edu.kantseryk.project.repository.ItemRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
/*
  @author Alona
  @project project
  @class RepositoryTest
  @version 1.0.0
  @since 07.05.2025 - 21.43
*/

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
public class RepositoryTest {

    @Autowired
    ItemRepository underTest;

    @BeforeAll
    void beforeAll() {}

    @BeforeEach
    void setUp() {
        Item freddy = new Item("1", "Freddy Mercury", "Queen", "###test");
        Item paul = new Item("2", "Paul McCartney", "Beatles", "###test");
        Item mick = new Item("3", "Mick Jagger", "Rolling Stones", "###test");
        underTest.saveAll(List.of(freddy, paul, mick));
    }

    @AfterEach
    void tearDown() {
        List<Item> itemsToDelete = underTest.findAll().stream()
                .filter(item -> item.getDescription().contains("###test"))
                .toList();
        underTest.deleteAll(itemsToDelete);
    }

    @AfterAll
    void afterAll() {}

    @Test
    void testSetShouldContains_3_Records_ToTest(){
        List<Item> itemsToDelete = underTest.findAll().stream()
                .filter(item -> item.getDescription().contains("###test"))
                .toList();
        assertEquals(3,itemsToDelete.size());
    }

    @Test
    void shouldGiveIdForNewRecord() {
        // given
        Item john = new Item("John Lennon", "Beatles", "###test");
        // when
        underTest.save(john);
        Item itemFromDb = underTest.findAll().stream()
                .filter(item -> item.getName().equals("John Lennon"))
                .findFirst().orElse(null);
        // then
        assertFalse(itemFromDb.getId() == john.getId());
        assertNotNull(itemFromDb);
        assertNotNull(itemFromDb.getId());
        assertFalse(itemFromDb.getId().isEmpty());
        assertEquals(24, itemFromDb.getId().length());
    }@Test
    void whenRecordHasIdThenItIsPossibleToSave() {
        // given
        Item john = Item.builder()
                .id("1")
                .description("###test2")
                .build();
        // when
        underTest.save(john);
        Item itemFromDb = underTest.findAll().stream()
                .filter(item -> item.getDescription().equals("###test2"))
                .findFirst().orElse(null);
        // then

        assertNotNull(itemFromDb);
    }

    //  id = "";

    // upgrade  - 2 test
    // find by Code native query


}