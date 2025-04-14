package com.db.taskcrud.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestTaskDto {

    private String name;
    private String description;
    private String status;
    private Long ownerId;
}
