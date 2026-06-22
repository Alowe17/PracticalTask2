package com.example.demo.mapper.task;

import com.example.demo.model.dto.task.CreateTaskRq;
import com.example.demo.model.dto.task.TaskDto;
import com.example.demo.model.entity.task.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "task.id", target = "number")
    TaskDto toDto(Task task);
    Task toEntity(CreateTaskRq createTaskRq);
}