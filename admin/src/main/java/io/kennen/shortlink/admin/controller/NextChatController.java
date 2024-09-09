package io.kennen.shortlink.admin.controller;

import io.kennen.shortlink.admin.common.convention.result.Result;
import io.kennen.shortlink.admin.common.convention.result.Results;
import io.kennen.shortlink.admin.dao.entity.VisitorDO;
import io.kennen.shortlink.admin.dto.req.CompletionBody;
import io.kennen.shortlink.admin.dto.req.CompletionMessage;
import io.kennen.shortlink.admin.dto.resp.ChatCompletionResult;
import io.kennen.shortlink.admin.service.NextChatService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.List;

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
     * 获取聊天标题
     */
    @PostMapping("/api/next-chat/completion/title")
    public Result requestChatTitle(@RequestBody CompletionBody body) {
        try {
            List<CompletionMessage> messages = body.getMessages();
            messages.add(new CompletionMessage( "user", "请用一句话为当前聊天取一个恰当的标题，输出的标题文字请包裹在两个|中，比如：当前标题可以是：|简单的交流|"));
            ChatCompletionResult completion = nextChatService.chatCompletionNonStream(body);
            String result = completion.getResult();
            result = result.substring(result.indexOf('|') + 1, result.lastIndexOf('|'));
            return Results.success(result);
        } catch (IOException e) {
            return Results.failure(e.getMessage());
        }
    }

    /**
     * 流式响应对话
     */
    @PostMapping("/api/next-chat/completion/stream")
    public ResponseBodyEmitter completionStream(@RequestBody CompletionBody body, HttpServletRequest request) {
        return nextChatService.chatCompletionStream(body, request);
    }

    /**
     * 点击 github 链接
     */
    @PostMapping("/api/next-chat/tracking/github")
    public void eventTracking(HttpServletRequest request) {
        nextChatService.eventTrack(request);
    }

    @GetMapping("/api/next-chat/tracking/get-kennen-chat-all-tracking-data")
    public Result<List<VisitorDO>> getTracking() {
        return Results.success(nextChatService.getAllTrack());
    }
}
