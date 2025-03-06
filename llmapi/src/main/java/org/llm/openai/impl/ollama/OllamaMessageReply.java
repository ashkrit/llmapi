package org.llm.openai.impl.ollama;

import org.llm.openai.model.ChatMessageReply;

import java.util.ArrayList;
import java.util.List;

public class OllamaMessageReply implements ChatMessageReply {
    public OllamaMessage message;

    @Override
    public String message() {
        return message.content;
    }

    public static class OllamaMessage {
        public String role;
        public String content;
    }

}
