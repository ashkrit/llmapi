package org.llm.openai.impl.groq;

import org.llm.openai.GenerativeAIService;
import org.llm.openai.model.ChatMessageReply;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.impl.openai.OpenAIConversationRequest;
import org.rpc.service.RpcBuilder;

import java.util.Map;

public class GroqGenerativeAIService implements GenerativeAIService {
    private final Map<String, Object> properties;
    private final GroqService service;

    public GroqGenerativeAIService(String url, Map<String, Object> properties) {
        this.properties = properties;
        this.service = new RpcBuilder().serviceUrl(url).create(GroqService.class);
    }

    @Override
    public ChatMessageReply chat(ChatRequest conversation) {

        var apiKey = "Bearer " + properties.get("apiKey");
        var message = new OpenAIConversationRequest(conversation.model());

        //append conversation messages to the message
        conversation.messages().forEach(m -> {
            message.append(m.role(), m.content());
        });

        var result = service.chat(apiKey, message);
        result.execute();

        //check for errors
        if (!result.isSuccess()) {
            throw new RuntimeException(result.error().get(), result.exception().get());
        }

        return result.value();
    }
}
