package org.llm.openai.impl.openai;

import java.util.ArrayList;
import java.util.List;

public class OpenAIConversationRequest {

    public final List<Message> messages = new ArrayList<>();
    public final String model;
    public final float temperature;
    public final int max_tokens = 1024;
    public final int top_p = 1;
    public final boolean stream;

    public OpenAIConversationRequest(String model, float temperature, boolean stream) {
        this.model = model;
        this.temperature = temperature;
        this.stream = stream;
    }

    // constructor with temp 1
    public OpenAIConversationRequest(String model) {
        this(model, 1, false);
    }


    public void append(String role, String content) {
        this.messages.add(new Message(role, content));
    }


    public record Message(String role, String content) {
    }
}