package com.db.taskcrud.unit;

import com.db.taskcrud.dto.request.RequestTaskDto;
import com.db.taskcrud.dto.response.ResponsePersonDto;
import com.db.taskcrud.dto.response.ResponseTaskDto;
import com.db.taskcrud.exception.NotFoundException;
import com.db.taskcrud.model.Person;
import com.db.taskcrud.model.Task;
import com.db.taskcrud.repository.TaskRepository;
import com.db.taskcrud.service.TaskService;
import com.db.taskcrud.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private final Long taskId = 1L;
    private final Long ownerId = 1L;
    private RequestTaskDto requestTaskDto;
    private Task task;

    @BeforeEach
    public void setup(){
        requestTaskDto = RequestTaskDto.builder()
                .name("Study Java")
                .description("Study for Java Test")
                .status("To do")
                .ownerId(ownerId).build();

        Person owner = Person.builder()
                .id(ownerId)
                .name("John Doe")
                .email("john@example.com")
                .password("Password123").build();

        task = Task.builder()
                .id(taskId)
                .name(requestTaskDto.getName())
                .description(requestTaskDto.getDescription())
                .status(requestTaskDto.getStatus()).build();

    }

    @Test
    public void getTaskById_ShouldReturnTask_WhenTaskExists(){
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        ResponseTaskDto result = taskService.getTaskById(taskId);

        assertNotNull(result);
        assertEquals(task.getName(), result.getName());
        assertEquals(task.getName(), result.getName());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void getTaskById_ShouldThrowNotFoundException_WhenTaskDoesNotExist() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.getTaskById(taskId));
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void createTask_ShouldSaveNewTask() {
        Task auxTask = Task.builder()
                        .name(requestTaskDto.getName())
                        .description(requestTaskDto.getDescription())
                        .status(requestTaskDto.getStatus()).build();

        when(taskRepository.save(auxTask)).thenReturn(task);

        ResponseTaskDto result = taskService.createTask(requestTaskDto);

        assertNotNull(result);
        assertEquals(task.getName(), result.getName());
        assertEquals(task.getName(), result.getName());
        verify(taskRepository, times(1)).save(auxTask);
    }


}
