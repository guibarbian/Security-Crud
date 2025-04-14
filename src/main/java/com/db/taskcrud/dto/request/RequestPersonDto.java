package com.db.taskcrud.dto.request;

import com.db.taskcrud.enums.PersonRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestPersonDto {
    private String name;
    private String email;
    private String password;
    private PersonRole role;
}
