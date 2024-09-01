package io.kennen.shortlink.admin.dto.resp;

import lombok.Data;
  
@Data  
public class ChatCompletionResult {  
    private String id;  
    private String object;  
    private long created;  
    private String result;  
    private boolean isTruncated;  
    private boolean needClearHistory;  
    private Usage usage;  
  
    @Data  
    public static class Usage {  
        private int promptTokens;  
        private int completionTokens;  
        private int totalTokens;  
    }  
}