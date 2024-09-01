package io.kennen.shortlink.admin.controller;

import io.kennen.shortlink.admin.common.convention.result.Result;
import io.kennen.shortlink.admin.common.convention.result.Results;
import io.kennen.shortlink.admin.dto.req.CompletionBody;
import io.kennen.shortlink.admin.dto.resp.ChatCompletionResult;
import io.kennen.shortlink.admin.service.NextChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class NextChatController {

    private final NextChatService nextChatService;

    /**
     * 非流式响应对话
     */
    @PostMapping("/api/next-chat/completion/nonstream")
    public Result completionNonStream(@RequestBody CompletionBody body) {
        try {
            ChatCompletionResult completion = nextChatService.chatCompletionNonStream(body);
            return Results.success(completion.getResult());
        } catch (IOException e) {
            return Results.failure(e.getMessage());
        }
    }

    /**
     * 流式响应对话
     */
    @PostMapping("/api/next-chat/completion/stream")
    public ResponseBodyEmitter completionStream(@RequestBody CompletionBody body) {
        return nextChatService.chatCompletionStream(body);
    }
}
