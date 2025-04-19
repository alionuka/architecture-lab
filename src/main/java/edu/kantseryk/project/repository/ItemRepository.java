package edu.kantseryk.project.repository;

/*
  @author Alona
  @project project
  @class ItemRepository
  @version 1.0.0
  @since 19.04.2025 - 13.18
*/

import edu.kantseryk.project.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
}