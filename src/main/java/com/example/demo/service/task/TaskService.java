package com.example.demo.service.task;

import com.example.demo.exception.custom.TaskInvalidDataException;
import com.example.demo.exception.custom.TaskNotFoundException;
import com.example.demo.mapper.task.TaskMapper;
import com.example.demo.model.dto.account.AccountDto;
import com.example.demo.model.dto.task.CreateTaskRq;
import com.example.demo.model.dto.task.TaskDto;
import com.example.demo.model.dto.task.UpdateTaskRq;
import com.example.demo.model.entity.account.Account;
import com.example.demo.model.entity.task.Task;
import com.example.demo.repository.task.TaskRepository;
import com.example.demo.service.account.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final AccountService accountService;

    public TaskDto getTaskById (Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new TaskNotFoundException("Не удалось найти задачу по указанному номеру!"));
    }

    public List<TaskDto> getTasksByAccount (AccountDto dto) {
        Account account = accountService.getAccountById(dto.getId());
        return taskRepository.findAllByAccount(account)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Transactional
    public void create (CreateTaskRq createTaskRq) {
        taskRepository.save(taskMapper.toEntity(createTaskRq));
    }

    @Transactional
    public void complete (Long id, AccountDto dto) {
        Account account = accountService.getAccountById(dto.getId());
        Task task = taskRepository.findByIdAndAccount(id, account)
                .orElseThrow(() -> new TaskNotFoundException("Не удалось найти задачу по указанному номеру!"));

        if (task.isCompleted()) {
            return;
        }

        task.setCompleted(true);
    }

    @Transactional
    public void deleteTask (Long id, AccountDto dto) {
        Account account = accountService.getAccountById(dto.getId());
        if (!taskRepository.existsByIdAndAccount(id, account)) {
            throw new TaskNotFoundException("Не удалось найти задачу!");
        }

        taskRepository.deleteById(id);
    }

    @Transactional
    public void updateTask (Long id, UpdateTaskRq updateTaskRq, AccountDto dto) {
        if (isUpdateDataEmpty(updateTaskRq)) {
            throw new TaskInvalidDataException("Нет данных для обновления задачи!");
        }

        Account account = accountService.getAccountById(dto.getId());
        Task task = taskRepository.findByIdAndAccount(id, account)
                .orElseThrow(() -> new TaskNotFoundException("Не удалось найти задачу по указанному номеру!"));

        if (updateTaskRq.getTitle() != null) {
            task.setTitle(updateTaskRq.getTitle().trim());
        }

        if (updateTaskRq.getDescription() != null) {
            task.setDescription(updateTaskRq.getDescription().trim());
        }

        if (updateTaskRq.getCompleted() != null && updateTaskRq.getCompleted() != task.isCompleted()) {
            task.setCompleted(updateTaskRq.getCompleted());
        }
    }

    private boolean isUpdateDataEmpty(UpdateTaskRq rq) {
        return (rq.getTitle() == null || rq.getTitle().isBlank())
                && (rq.getDescription() == null || rq.getDescription().isBlank())
                && rq.getCompleted() == null;
    }

    @Transactional
    public void changeTaskOwner (Long taskId, Long newOwnerId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Не удалось найти задачу по указанному номеру!"));

        Account newOwner = accountService.getAccountById(newOwnerId);
        task.setAccount(newOwner);
    }
}