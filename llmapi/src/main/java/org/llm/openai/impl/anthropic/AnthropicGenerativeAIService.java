package org.llm.openai.impl.anthropic;

import org.llm.openai.GenerativeAIService;
import org.llm.openai.model.ChatMessageReply;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.impl.openai.OpenAIConversationRequest;
import org.rpc.service.RpcBuilder;

import java.util.Map;

public class AnthropicGenerativeAIService implements GenerativeAIService {
    private final Map<String, Object> properties;
    private final AnthropicService service;

    public AnthropicGenerativeAIService(String url, Map<String, Object> properties) {
        this.properties = properties;
        this.service = new RpcBuilder().serviceUrl(url).create(AnthropicService.class);
    }

    @Override
    public ChatMessageReply chat(ChatRequest conversation) {

        var apiKey = (String) properties.get("apiKey");
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
