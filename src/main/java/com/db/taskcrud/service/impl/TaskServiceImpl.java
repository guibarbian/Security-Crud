package com.db.taskcrud.service.impl;

import com.db.taskcrud.dto.request.RequestTaskDto;
import com.db.taskcrud.dto.response.ResponseTaskDto;
import com.db.taskcrud.exception.NotFoundException;
import com.db.taskcrud.model.Task;
import com.db.taskcrud.repository.TaskRepository;
import com.db.taskcrud.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public ResponseTaskDto getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);

        if(task.isEmpty()){
            throw new NotFoundException("Task not found");
        }

        return task.get().toDto();
    }

    @Override
    public ResponseTaskDto createTask(RequestTaskDto taskDto){
        Task newTask = Task.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus()).build();

        return taskRepository.save(newTask).toDto();
    }

    @Override
    public ResponseTaskDto updateTask(Long id, RequestTaskDto taskDto) {
        Optional<Task> task = taskRepository.findById(id);

        if(task.isEmpty()){
            throw new NotFoundException("Task not found");
        }

        Task updatedTask = Task.builder()
                .id(id)
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus()).build();

        return taskRepository.save(updatedTask).toDto();
    }

    @Override
    public void deleteTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);

        if(task.isEmpty()){
            throw new NotFoundException("Task not found");
        }

        taskRepository.deleteById(id);
    }

    ;
}
