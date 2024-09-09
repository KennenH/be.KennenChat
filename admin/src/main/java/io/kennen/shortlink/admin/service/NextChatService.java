package io.kennen.shortlink.admin.service;

import io.kennen.shortlink.admin.dao.entity.VisitorDO;
import io.kennen.shortlink.admin.dto.req.CompletionBody;
import io.kennen.shortlink.admin.dto.resp.ChatCompletionResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.List;

public interface NextChatService {

    /**
     * 非流式输出
     */
    ChatCompletionResult chatCompletionNonStream(CompletionBody body) throws IOException;

    /**
     * 流式输出
     */
    ResponseBodyEmitter chatCompletionStream(CompletionBody body, HttpServletRequest request);

    /**
     * 埋点
     */
    void eventTrack(HttpServletRequest request);

    List<VisitorDO> getAllTrack();
}
