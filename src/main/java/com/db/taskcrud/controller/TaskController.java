package com.db.taskcrud.controller;

import com.db.taskcrud.dto.request.RequestTaskDto;
import com.db.taskcrud.dto.response.ResponseTaskDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskController {

    ResponseEntity<ResponseTaskDto> getTaskById(Long id);

    ResponseEntity<ResponseTaskDto> createTask(RequestTaskDto taskDto);

    ResponseEntity<ResponseTaskDto> updateTask(Long id, RequestTaskDto taskDto);

    ResponseEntity<String> deleteTask(Long id);
}
