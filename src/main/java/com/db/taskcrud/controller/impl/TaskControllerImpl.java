package com.db.taskcrud.controller.impl;

import com.db.taskcrud.controller.TaskController;
import com.db.taskcrud.dto.request.RequestTaskDto;
import com.db.taskcrud.dto.response.ResponseTaskDto;
import com.db.taskcrud.repository.TaskRepository;
import com.db.taskcrud.service.impl.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskControllerImpl implements TaskController {

    private final TaskServiceImpl taskService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTaskDto> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<ResponseTaskDto> createTask(@RequestBody RequestTaskDto taskDto){
        return ResponseEntity.status(201).body(taskService.createTask(taskDto));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseTaskDto> updateTask(@PathVariable Long id, @RequestBody RequestTaskDto taskDto){
        return ResponseEntity.ok(taskService.updateTask(id, taskDto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted");
    }


}
