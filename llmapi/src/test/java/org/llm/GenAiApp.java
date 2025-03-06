package org.llm;

import org.llm.openai.GenerativeAIDriverManager;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.openai.OpenAIFactory;

import java.util.List;
import java.util.Map;

import static org.llm.openai.GenerativeAIDriverManager.registerService;

public class GenAiApp {

    public static void main(String[] args) {

        registerService("openai", new OpenAIFactory());

        Map<String, Object> properties = Map.of("apiKey", System.getenv("gpt_key"));
        var service = GenerativeAIDriverManager.create("openai", "https://api.openai.com/", properties);

        System.out.println(service);

        var messages = new ChatRequest.ChatMessage("user", "Hello, how are you?");
        var conversation = new ChatRequest("gpt-4o-mini", List.of(messages));
        var reply = service.chat(conversation);

        System.out.println(reply.message());

    }
}
