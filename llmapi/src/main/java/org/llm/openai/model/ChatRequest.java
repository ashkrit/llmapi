package org.llm.openai.model;

import java.util.List;

public record ChatRequest(String model, float temperature, List<ChatMessage> messages) {

    public record ChatMessage(String role, String content) {

    }

    public static ChatRequest create(String model, List<ChatMessage> messages) {
        return new ChatRequest(model, 1.0f, messages);
    }
}
