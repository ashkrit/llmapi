package org.llm.openai.openai;

import org.llm.openai.GenerativeAIService;
import org.llm.openai.model.ChatMessageReply;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.model.internal.Conversation;
import org.rpc.service.RpcBuilder;

import java.util.Map;

public class OpenAIGenerativeAIService implements GenerativeAIService {
    private final Map<String, Object> properties;
    private final OpenAIService service;

    public OpenAIGenerativeAIService(String url, Map<String, Object> properties) {
        this.properties = properties;
        this.service = new RpcBuilder().serviceUrl(url).create(OpenAIService.class);
    }

    @Override
    public ChatMessageReply chat(ChatRequest conversation) {

        var apiKey = "Bearer " + properties.get("apiKey");
        var message = new Conversation(conversation.model());

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
