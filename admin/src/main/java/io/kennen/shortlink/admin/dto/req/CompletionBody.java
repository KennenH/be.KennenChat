package io.kennen.shortlink.admin.dto.req;

import lombok.Data;

import java.util.List;

@Data
public class CompletionBody {

    private List<CompletionMessage> messages;

    private double temperature = 0.95;

    private double top_p = 0.7;

    private double penalty_score = 1.0;
}
