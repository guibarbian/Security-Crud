package com.db.taskcrud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record AuthenticationResponse(String token, String refreshToken) {
}
