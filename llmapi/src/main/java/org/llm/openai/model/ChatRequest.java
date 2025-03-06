package org.llm.openai.model;

import java.util.List;

public record ChatRequest(String model, List<ChatMessage> messages) {

    public record ChatMessage(String role, String content) {

    }
}
