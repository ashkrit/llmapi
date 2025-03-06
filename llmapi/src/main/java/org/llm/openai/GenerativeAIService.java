package org.llm.openai;

import org.llm.openai.model.ChatMessageReply;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.model.EmbeddingReply;
import org.llm.openai.model.EmbeddingRequest;

public interface GenerativeAIService {
    ChatMessageReply chat(ChatRequest conversation);

    default EmbeddingReply embedding(EmbeddingRequest embedding) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
