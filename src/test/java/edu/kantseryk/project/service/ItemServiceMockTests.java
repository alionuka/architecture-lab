package edu.kantseryk.project.service;

import edu.kantseryk.project.model.Item;
import edu.kantseryk.project.repository.ItemRepository;
import edu.kantseryk.project.request.ItemCreateRequest;
import edu.kantseryk.project.request.ItemUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceMockTests {

    @Mock
    private ItemRepository mockRepository;

    @InjectMocks
    private ItemService underTest;

    @Captor
    private ArgumentCaptor<Item> argumentCaptor;

    @Test
    void whenInsertNewItemAndCodeNotExistsThenOk() {
        ItemCreateRequest request = new ItemCreateRequest("Till", "Rammstein", "poet");
        given(mockRepository.existsByCode(request.code())).willReturn(false);

        underTest.create(request);

        verify(mockRepository).save(argumentCaptor.capture());
        Item itemToSave = argumentCaptor.getValue();
        assertThat(itemToSave.getName()).isEqualTo(request.name());
        assertNotNull(itemToSave.getCreateDate());
        assertTrue(itemToSave.getUpdateDate().isEmpty());
    }

    @Test
    void whenInsertNewItemAndCodeExistsThenReturnNull() {
        ItemCreateRequest request = new ItemCreateRequest("Till", "Rammstein", "vocal");
        given(mockRepository.existsByCode(request.code())).willReturn(true);

        Item result = underTest.create(request);

        assertNull(result);
        verify(mockRepository, never()).save(any());
    }

    @Test
    void whenGetAllThenReturnsList() {
        List<Item> mockItems = List.of(
                new Item("1", "Name1", "Code1", "Desc1"),
                new Item("2", "Name2", "Code2", "Desc2")
        );
        given(mockRepository.findAll()).willReturn(mockItems);

        List<Item> result = underTest.getAll();

        assertEquals(2, result.size());
        assertEquals("Name1", result.get(0).getName());
    }

    @Test
    void whenGetByIdExistsThenReturnItem() {
        Item item = new Item("1", "Name", "Code", "Desc");
        given(mockRepository.findById("1")).willReturn(Optional.of(item));

        Item result = underTest.getById("1");

        assertNotNull(result);
        assertEquals("Name", result.getName());
    }

    @Test
    void whenGetByIdNotExistsThenReturnNull() {
        given(mockRepository.findById("2")).willReturn(Optional.empty());

        Item result = underTest.getById("2");

        assertNull(result);
    }

    @Test
    void whenUpdateItemThenSaveCalled() {
        Item item = new Item("1", "Name", "Code", "Desc");
        underTest.update(item);

        verify(mockRepository).save(item);
    }

    @Test
    void whenDeleteByIdThenRepositoryDeleteCalled() {
        underTest.delById("1");

        verify(mockRepository).deleteById("1");
    }

    @Test
    void whenUpdateWithRequestExistsThenUpdateDateAdded() {
        Item existing = Item.builder()
                .id("1").name("Old").code("C1").description("Desc")
                .createDate(LocalDateTime.now())
                .updateDate(new ArrayList<>())
                .build();

        ItemUpdateRequest updateRequest = new ItemUpdateRequest("1", "New", "C1", "New Desc");

        given(mockRepository.findById("1")).willReturn(Optional.of(existing));
        given(mockRepository.save(any())).willAnswer(inv -> inv.getArgument(0));

        Item result = underTest.update(updateRequest);

        assertEquals("New", result.getName());
        assertEquals(1, result.getUpdateDate().size());
    }

    @Test
    void whenUpdateWithRequestNotFoundThenReturnNull() {
        ItemUpdateRequest updateRequest = new ItemUpdateRequest("99", "X", "C", "Y");
        given(mockRepository.findById("99")).willReturn(Optional.empty());

        Item result = underTest.update(updateRequest);

        assertNull(result);
        verify(mockRepository, never()).save(any());
    }

    @Test
    void whenCreateItemDirectlyThenSaveCalled() {
        Item item = new Item("X", "Y", "Z");
        underTest.create(item);

        verify(mockRepository).save(item);
    }

    @Test
    void whenUpdateItemThenCreateDateShouldRemainUnchanged() {
        LocalDateTime createTime = LocalDateTime.now().minusDays(1);
        Item existing = Item.builder()
                .id("1").name("Old").code("C1").description("Desc")
                .createDate(createTime)
                .updateDate(new ArrayList<>())
                .build();

        ItemUpdateRequest updateRequest = new ItemUpdateRequest("1", "Updated", "C1", "Updated Desc");

        given(mockRepository.findById("1")).willReturn(Optional.of(existing));
        given(mockRepository.save(any())).willAnswer(invocation -> invocation.getArgument(0));

        Item result = underTest.update(updateRequest);

        assertEquals(createTime, result.getCreateDate(), "Create date must not be changed");
    }

    @Test
    void whenUpdateWithNullNameThenReturnNull() {
        ItemUpdateRequest badRequest = new ItemUpdateRequest("1", null, "C1", "New Desc");

        Item existing = Item.builder()
                .id("1").name("Old").code("C1").description("Desc")
                .createDate(LocalDateTime.now())
                .updateDate(new ArrayList<>())
                .build();

        Item result = underTest.update(badRequest);

        assertNull(result);
        verify(mockRepository, never()).save(any());
    }

}
