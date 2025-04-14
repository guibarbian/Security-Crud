package com.db.taskcrud.service;

import com.db.taskcrud.dto.request.RequestTaskDto;
import com.db.taskcrud.dto.response.ResponseTaskDto;
import com.db.taskcrud.model.Task;

import java.util.List;

public interface TaskService {

    ResponseTaskDto getTaskById(Long id);

    ResponseTaskDto createTask(RequestTaskDto taskDto);

    ResponseTaskDto updateTask(Long id, RequestTaskDto taskDto);

    void deleteTask(Long id);


}
