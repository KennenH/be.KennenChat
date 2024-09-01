package io.kennen.shortlink.admin.service.impl;

import cn.hutool.json.JSONUtil;
import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.core.builder.ChatBuilder;
import com.baidubce.qianfan.model.chat.Message;
import com.google.gson.Gson;
import io.kennen.shortlink.admin.dto.req.CompletionBody;
import io.kennen.shortlink.admin.dto.req.CompletionMessage;
import io.kennen.shortlink.admin.dto.resp.ChatCompletionResult;
import io.kennen.shortlink.admin.service.NextChatService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
// 使用构造器注入方式注入userRegisterCachePenetrationBloomFilter，在Autowired或Resource的基础上对属性加一个final修饰即可
@RequiredArgsConstructor
public class NextChatServiceImpl implements NextChatService {

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient()
            .newBuilder()
            .build();
    private final Gson gson = new Gson();

    @Override
    public ChatCompletionResult chatCompletionNonStream(CompletionBody body) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(
                mediaType,
                gson.toJson(body)
        );
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie-lite-8k?access_token=" + getAccessToken())
                .method("POST", requestBody)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        String result = response.body().string();
        return JSONUtil.toBean(result, ChatCompletionResult.class);
    }

    private static final ChatBuilder builder = new Qianfan(AK, SK).chatCompletion().model("ERNIE-Speed-8K");

    private static final BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>(100);

    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            2, 4, 10L, TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * 销毁线程池
     */
    @PreDestroy
    public void shutdownThreadPool() {
        threadPool.shutdown();
    }

    @Override
    public ResponseBodyEmitter chatCompletionStream(CompletionBody body) {
        List<CompletionMessage> prompts = body.getMessages();
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        threadPool.submit(() -> {
            List<Message> messages = new ArrayList<>();
            for (CompletionMessage prompt : prompts) {
                messages.add(new Message().setRole(prompt.getRole()).setContent(prompt.getContent()));
            }
            builder
                    .messages(messages)
                    .temperature(body.getTemperature())
                    .topP(body.getTop_p())
                    .penaltyScore(body.getPenalty_score());
            try {
                builder.executeStream().forEachRemaining(chunk -> {
                    try {
                        emitter.send(chunk.getResult());
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                });
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    /**
     * AK，SK生成 Access Token
     */
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
}
