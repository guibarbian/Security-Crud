package com.db.taskcrud.dto;

import com.db.taskcrud.enums.PersonRole;

public record RegisterDTO(String name, String email, String password) {
}
