package com.db.taskcrud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTaskDto {

    private Long id;
    private String name;
    private String description;
    private String status;
}
