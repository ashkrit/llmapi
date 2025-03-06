package org.llm.openai;

import org.llm.openai.model.ChatMessageReply;
import org.llm.openai.model.ChatRequest;

public interface GenerativeAIService {
    ChatMessageReply chat(ChatRequest conversation);
}
