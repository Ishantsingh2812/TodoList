package com.ishant.TodoList.repository;

import com.ishant.TodoList.entity.Todos;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TodosRepository extends MongoRepository<Todos, ObjectId> {
    List<Todos> findByOwnerId(ObjectId ownerId);
}

