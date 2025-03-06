package org.llm.openai.impl.ollama;

import org.llm.openai.GenerativeAIService;
import org.llm.openai.impl.openai.OpenAIConversationReply;
import org.llm.openai.impl.openai.OpenAIConversationRequest;
import org.llm.openai.impl.openai.OpenAIService;
import org.llm.openai.model.ChatMessageReply;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.model.EmbeddingReply;
import org.llm.openai.model.EmbeddingRequest;
import org.rpc.service.RpcBuilder;
import org.rpc.service.RpcReply;

import java.util.Map;

public class OllamaGenerativeAIService implements GenerativeAIService {
    private final OllamaService service;


    public OllamaGenerativeAIService(String url, Map<String, Object> properties) {
        this.service = new RpcBuilder().serviceUrl(url).create(OllamaService.class);

    }

    @Override
    public ChatMessageReply chat(ChatRequest conversation) {

        var message = new OpenAIConversationRequest(conversation.model());

        //append conversation messages to the message
        conversation.messages().forEach(m -> {
            message.append(m.role(), m.content());
        });

        var result = service.chat(message);
        result.execute();

        //check for errors
        if (!result.isSuccess()) {
            throw asREException(result);
        }

        return result.value();
    }

    private static RuntimeException asREException(RpcReply<OllamaMessageReply> result) {
        return new RuntimeException(result.error().get(), result.exception().get());
    }

    @Override
    public EmbeddingReply embedding(EmbeddingRequest embedding) {

        var result = service.embedding(embedding);

        result.execute();
        //check for errors
        if (!result.isSuccess()) {
            throw new RuntimeException(result.error().get(), result.exception().get());
        }

        return result.value();

    }
}
