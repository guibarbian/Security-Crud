package com.db.taskcrud.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshDTO(@NotBlank String refreshToken) {
}
