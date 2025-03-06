package org.llm.openai;

import org.llm.openai.model.ChatMessageReply;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.model.EmbeddingReply;
import org.llm.openai.model.EmbeddingRequest;
import org.llm.openai.param.ParamPreparedRequest;
import org.llm.openai.parser.ChatMessageJsonParser;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public interface GenerativeAIService {
    ChatMessageReply chat(ChatRequest conversation);

    default ChatRequest prepareRequest(ChatRequest conversation, Map<String, Object> params) {
        return ParamPreparedRequest.prepare(conversation, params);
    }

    default EmbeddingReply embedding(EmbeddingRequest embedding) {
        throw new UnsupportedOperationException("Not Supported");
    }

    default <T> T chat(ChatRequest conversation, Class<T> returnType) {
        return chat(conversation, returnType, (jsonContent, e) -> {
            throw new RuntimeException("Failed to parse JSON: " + jsonContent, e);
        }).get();
    }

    default <T> Optional<T> chat(ChatRequest conversation, Class<T> returnType, BiConsumer<String, Exception> onFailedParsing) {
        var reply = chat(conversation);
        return ChatMessageJsonParser.parse(reply, returnType, onFailedParsing);
    }
}
