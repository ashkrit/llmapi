package org.llm.openai.impl.google;

import org.llm.openai.GenerativeAIService;
import org.llm.openai.model.ChatMessageReply;
import org.llm.openai.model.ChatRequest;
import org.rpc.service.RpcBuilder;

import java.util.Map;

public class GoogleGenerativeAIService implements GenerativeAIService {
    private final Map<String, Object> properties;
    private final GoogleService service;

    public GoogleGenerativeAIService(String url, Map<String, Object> properties) {
        this.properties = properties;
        this.service = new RpcBuilder().serviceUrl(url).create(GoogleService.class);
    }

    @Override
    public ChatMessageReply chat(ChatRequest conversation) {

        var apiKey = (String) properties.get("apiKey");
        var message = new GoogleConversation();
        message.generationConfig = new GoogleGenerationConfig();
        message.generationConfig.temperature = conversation.temperature();


        //append conversation messages to the message
        conversation.messages().forEach(m -> {
            message.append(m.content());
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
