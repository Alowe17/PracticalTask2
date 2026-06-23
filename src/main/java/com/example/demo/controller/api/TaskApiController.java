package com.example.demo.controller.api;

import com.example.demo.model.dto.account.AccountDto;
import com.example.demo.model.dto.task.CreateTaskRq;
import com.example.demo.model.dto.task.TaskDto;
import com.example.demo.model.dto.task.UpdateTaskRq;
import com.example.demo.service.task.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskApiController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks (AccountDto accountDto) {
        return ResponseEntity.ok(taskService.getTasksByAccount(accountDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById (@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createTask (@Valid @RequestBody CreateTaskRq createTaskRq) {
        taskService.create(createTaskRq);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<Void> completeTask (@PathVariable Long id, AccountDto accountDto) {
        taskService.complete(id, accountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask (@PathVariable Long id, @RequestBody UpdateTaskRq updateTaskRq, AccountDto accountDto) {
        taskService.updateTask(id, updateTaskRq, accountDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask (@PathVariable Long id, AccountDto accountDto) {
        taskService.deleteTask(id, accountDto);
        return ResponseEntity.ok().build();
    }
}