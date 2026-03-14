package com.ishant.TodoList.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "todos")
@Data
@NoArgsConstructor
public class Todos {
        @Id
        private ObjectId id;
        @NonNull
        private String title;

        private String description;

        private boolean completed;

        @NonNull
        private ObjectId ownerId;


}



