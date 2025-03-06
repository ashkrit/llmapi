package org.llm.openai.impl.openai;

import org.llm.openai.GenerativeAIService;
import org.llm.openai.model.ChatMessageReply;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.model.EmbeddingReply;
import org.llm.openai.model.EmbeddingRequest;
import org.rpc.service.RpcBuilder;
import org.rpc.service.RpcReply;

import java.util.Map;

public class OpenAIGenerativeAIService implements GenerativeAIService {
    private final OpenAIService service;
    private final String apiKey;

    public OpenAIGenerativeAIService(String url, Map<String, Object> properties) {
        this.service = new RpcBuilder().serviceUrl(url).create(OpenAIService.class);
        this.apiKey = "Bearer " + properties.get("apiKey");
    }

    @Override
    public ChatMessageReply chat(ChatRequest conversation) {

        var message = new OpenAIConversationRequest(conversation.model(),conversation.temperature(),false);

        //append conversation messages to the message
        conversation.messages().forEach(m -> {
            message.append(m.role(), m.content());
        });

        var result = service.chat(apiKey, message);
        result.execute();

        //check for errors
        if (!result.isSuccess()) {
            throw asREException(result);
        }

        return result.value();
    }

    private static RuntimeException asREException(RpcReply<OpenAIConversationReply> result) {
        return new RuntimeException(result.error().get(), result.exception().get());
    }

    @Override
    public EmbeddingReply embedding(EmbeddingRequest embedding) {

        var result = service.embedding(apiKey, embedding);

        result.execute();
        //check for errors
        if (!result.isSuccess()) {
            throw new RuntimeException(result.error().get(), result.exception().get());
        }

        return result.value();

    }
}
