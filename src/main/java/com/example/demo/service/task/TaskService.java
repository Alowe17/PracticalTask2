package com.example.demo.service.task;

import com.example.demo.exception.custom.TaskInvalidDataException;
import com.example.demo.exception.custom.TaskNotFoundException;
import com.example.demo.mapper.task.TaskMapper;
import com.example.demo.model.dto.task.CreateTaskRq;
import com.example.demo.model.dto.task.TaskDto;
import com.example.demo.model.dto.task.UpdateTaskRq;
import com.example.demo.model.entity.account.Account;
import com.example.demo.model.entity.task.Task;
import com.example.demo.repository.task.TaskRepository;
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

    public TaskDto getTaskById (Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Задача с id {} не найдена!", id);
                    return new TaskNotFoundException("Не удалось найти задачу по указанному номеру!");
                });
        // Не даю конкретики пользователю, чтобы не раскрывать внутреннюю структуру приложения и другую информацию
    }

    public List<TaskDto> getTasksByAccount (Account account) {
        return taskRepository.findAllByAccount(account)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Transactional
    public void create (CreateTaskRq createTaskRq) {
        taskRepository.save(taskMapper.toEntity(createTaskRq));
        log.info("Задача успешно создана!");
    }

    @Transactional
    public void complete (Long id, Account account) {
        Task task = taskRepository.findByIdAndAccount(id, account)
                .orElseThrow(() -> {
                    log.error("Задача с id {} не найдена для аккаунта {}!", id, account.getId());
                    return new TaskNotFoundException("Не удалось найти задачу по указанному номеру!");
                });

        if (task.isCompleted()) {
            log.warn("Задача с id {} уже выполнена!", id);
            return;
        }

        task.setCompleted(true);
    }

    @Transactional
    public void deleteTask (Long id, Account account) {
        if (!taskRepository.existsByIdAndAccount(id, account)) {
            log.error("Задача с id {} не принадлежит этому аккаунту {}!", id, account.getId());
            throw new TaskNotFoundException("Не удалось найти задачу!");
        }

        taskRepository.deleteById(id);
        log.info("Задача с id {} успешно удалена!", id);
    }

    @Transactional
    public void updateTask (Long id, UpdateTaskRq updateTaskRq, Account account) {
        if (isUpdateDataEmpty(updateTaskRq)) {
            log.warn("Нет данных для обновления задачи с id {}", id);
            throw new TaskInvalidDataException("Нет данных для обновления задачи!");
        }

        Task task = taskRepository.findByIdAndAccount(id, account)
                .orElseThrow(() -> {
                    log.error("Задача с id {} не найдена для аккаунта {}!", id, account.getId());
                    return new TaskNotFoundException("Не удалось найти задачу по указанному номеру!");
                });

        if (updateTaskRq.getTitle() != null && !updateTaskRq.getTitle().isBlank()) {
            task.setTitle(updateTaskRq.getTitle().trim());
        }

        if (updateTaskRq.getDescription() != null && !updateTaskRq.getDescription().isBlank()) {
            task.setDescription(updateTaskRq.getDescription().trim());
        }

        if (updateTaskRq.getCompleted() != null && updateTaskRq.getCompleted() != task.isCompleted()) {
            task.setCompleted(updateTaskRq.getCompleted());
        }

        log.info("Задача с id {} успешно обновлена!", id);
    }

    private boolean isUpdateDataEmpty(UpdateTaskRq rq) {
        return rq.getTitle() == null && rq.getDescription() == null && rq.getCompleted() == null;
    }
}