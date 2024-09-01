package io.kennen.shortlink.admin.service;

import io.kennen.shortlink.admin.dto.req.CompletionBody;
import io.kennen.shortlink.admin.dto.resp.ChatCompletionResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

public interface NextChatService {

    /**
     * 非流式输出
     */
    ChatCompletionResult chatCompletionNonStream(CompletionBody body) throws IOException;

    /**
     * 流式输出
     */
    ResponseBodyEmitter chatCompletionStream(CompletionBody body);
}
