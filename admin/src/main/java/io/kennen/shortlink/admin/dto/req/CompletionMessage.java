package io.kennen.shortlink.admin.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompletionMessage {
    public CompletionMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    private String role;
    private String content;
}