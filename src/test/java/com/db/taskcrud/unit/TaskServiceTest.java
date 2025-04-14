package com.db.taskcrud.unit;

import com.db.taskcrud.dto.request.RequestTaskDto;
import com.db.taskcrud.dto.response.ResponseTaskDto;
import com.db.taskcrud.exception.NotFoundException;
import com.db.taskcrud.model.Task;
import com.db.taskcrud.repository.TaskRepository;
import com.db.taskcrud.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    Task task = new Task(1L, "Save NYC", "Save the city from Sandman", "Done");
    RequestTaskDto requestDto = new RequestTaskDto("Defeat Venom", "Defeat Venom before he causes any troubl", "To do");

    @Test
    void testGetTaskById_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        ResponseTaskDto result = taskService.getTaskById(1L);

        assertEquals(task.getName(), result.getName());
        verify(taskRepository).findById(1L);
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void testCreateTask_Success() {
        Task auxTask = new Task(null, requestDto.getName(), requestDto.getDescription(), requestDto.getStatus());
        Task savedTask = new Task(2L, auxTask.getName(), auxTask.getDescription(), auxTask.getStatus());

        when(taskRepository.save(auxTask)).thenReturn(savedTask);

        ResponseTaskDto result = taskService.createTask(requestDto);

        assertEquals("Defeat Venom", result.getName());
        verify(taskRepository).save(auxTask);
    }

    @Test
    void testUpdateTask_Success() {
        Task auxTask = new Task(1L, requestDto.getName(), requestDto.getDescription(), requestDto.getStatus());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(auxTask)).thenReturn(auxTask);

        ResponseTaskDto result = taskService.updateTask(1L, requestDto);

        assertEquals("Defeat Venom", result.getName());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(auxTask);
    }

    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.updateTask(1L, requestDto));
    }

    @Test
    void testDeleteTask_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository).findById(1L);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.deleteTask(1L));
    }
}
