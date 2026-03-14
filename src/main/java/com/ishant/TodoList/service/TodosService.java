package com.ishant.TodoList.service;

import com.ishant.TodoList.DTOs.TodoRequest;
import com.ishant.TodoList.Exceptions.TodoNotFoundException;
import com.ishant.TodoList.Exceptions.UserNotFoundException;
import com.ishant.TodoList.entity.Todos;
import com.ishant.TodoList.entity.User;
import com.ishant.TodoList.repository.TodosRepository;
import com.ishant.TodoList.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TodosService {

    @Autowired
    private TodosRepository todosRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    public Todos createTodoForUser(String username, String title, String desc){

        User user = userService.findByUserName(username);

        if(user == null){
            throw new UserNotFoundException("User not found with username "+ username);
        }

        Todos todo = new Todos();
        todo.setTitle(title);
        todo.setDescription(desc);
        todo.setCompleted(false);
        todo.setOwnerId(user.getId());


        return todosRepository.save(todo);
    }


    public List<Todos> getTodosForUser(String username) {
        User user = userService.findByUserName(username);
        if (user == null) {
            throw new UserNotFoundException("User not found: " + username);
        }
        ObjectId ownerId = user.getId();


        return todosRepository.findByOwnerId(ownerId);
    }

    public Todos updateTodoForUser(String username , ObjectId todoId, TodoRequest todoRequest){
        User user = userService.findByUserName(username);

        Todos todo = todosRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("Tod not found with id: " + todoId));

        ObjectId userId = user.getId();
        if (!todo.getOwnerId().equals(userId)) {
            throw new UserNotFoundException("Todo does not belong to user with id: " + userId);
        }

        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setCompleted(todoRequest.isCompleted());

        return todosRepository.save(todo);

    }

    public void deleteTodoForUser(String username,ObjectId todoId){

        User user = userService.findByUserName(username);
        Todos todo = todosRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + todoId));
        ObjectId userId = user.getId();
        if (!todo.getOwnerId().equals(userId)) {
            throw new UserNotFoundException("Not authorized to delete this todo");
        }

        todosRepository.delete(todo);
    }

    public boolean completedTodo(String username, ObjectId todoId, TodoRequest todoRequest) {
        User user = userService.findByUserName(username);
        Todos todo = todosRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + todoId));
        ObjectId userId = user.getId();
        if (!todo.getOwnerId().equals(userId)) {
            throw new UserNotFoundException("Not authorized to update this todo");
        }
        todo.setCompleted(todoRequest.isCompleted());
        todosRepository.save(todo);

        return true;

    }

}
