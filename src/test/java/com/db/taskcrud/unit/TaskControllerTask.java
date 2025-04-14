package com.db.taskcrud.unit;

import com.db.taskcrud.controller.impl.TaskControllerImpl;
import com.db.taskcrud.dto.request.RequestTaskDto;
import com.db.taskcrud.dto.response.ResponseTaskDto;
import com.db.taskcrud.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerImplTest {

    @Mock
    private TaskServiceImpl taskService;

    @InjectMocks
    private TaskControllerImpl taskController;

    Long id = 1L;

    @Test
    void getTaskById_ShouldReturnTask() {
        ResponseTaskDto mockResponse = new ResponseTaskDto(id, "Task 1", "Desc", "TODO");

        when(taskService.getTaskById(id)).thenReturn(mockResponse);

        ResponseEntity<ResponseTaskDto> response = taskController.getTaskById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(taskService).getTaskById(id);
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {
        RequestTaskDto request = new RequestTaskDto("Task 2", "Desc 2", "IN_PROGRESS");
        ResponseTaskDto responseDto = new ResponseTaskDto(2L, "Task 2", "Desc 2", "IN_PROGRESS");

        when(taskService.createTask(request)).thenReturn(responseDto);

        ResponseEntity<ResponseTaskDto> response = taskController.createTask(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
        verify(taskService).createTask(request);
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() {
        Long id = 3L;
        RequestTaskDto request = new RequestTaskDto("Updated Task", "Updated Desc", "DONE");
        ResponseTaskDto updated = new ResponseTaskDto(id, "Updated Task", "Updated Desc", "DONE");

        when(taskService.updateTask(id, request)).thenReturn(updated);

        ResponseEntity<ResponseTaskDto> response = taskController.updateTask(id, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
        verify(taskService).updateTask(id, request);
    }

    @Test
    void deleteTask_ShouldReturnConfirmationMessage() {
        Long id = 4L;
        doNothing().when(taskService).deleteTask(id);

        ResponseEntity<String> response = taskController.deleteTask(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Task deleted", response.getBody());
        verify(taskService).deleteTask(id);
    }
}
