package com.db.taskcrud.dto.response;

import com.db.taskcrud.enums.PersonRole;
import com.db.taskcrud.model.Task;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponsePersonDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private PersonRole role;
}
