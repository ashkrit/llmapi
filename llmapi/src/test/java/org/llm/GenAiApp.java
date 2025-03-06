package org.llm;

import org.llm.openai.GenerativeAIDriverManager;
import org.llm.openai.anthropic.AnthropicAIFactory;
import org.llm.openai.google.GoogleAIFactory;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.model.ChatRequest.ChatMessage;
import org.llm.openai.openai.OpenAIFactory;

import java.util.List;
import java.util.Map;

import static org.llm.openai.GenerativeAIDriverManager.registerService;

public class GenAiApp {

    public static void main(String[] args) {

        registerService("openai", new OpenAIFactory());
        registerService("anthropic", new AnthropicAIFactory());
        registerService("google", new GoogleAIFactory());

        _openAi();
        _anthropicAi();
        _googleAi();


    }

    private static void _openAi() {
        Map<String, Object> properties = Map.of("apiKey", System.getenv("gpt_key"));
        var service = GenerativeAIDriverManager.create("openai", "https://api.openai.com/", properties);


        System.out.println(service);
        var messages = new ChatMessage("user", "Hello, how are you?");
        var conversation = new ChatRequest("gpt-4o-mini", List.of(messages));
        var reply = service.chat(conversation);
        System.out.println(reply.message());
    }

    private static void _anthropicAi() {
        Map<String, Object> properties = Map.of("apiKey", System.getenv("ANTHROPIC_API_KEY"));
        var service = GenerativeAIDriverManager.create("openai", "https://api.anthropic.com", properties);


        System.out.println(service);
        var messages = new ChatMessage("user", "Hello, how are you?");
        var conversation = new ChatRequest("claude-3-7-sonnet-20250219", List.of(messages));
        var reply = service.chat(conversation);
        System.out.println(reply.message());
    }


    private static void _googleAi() {
        Map<String, Object> properties = Map.of("apiKey", System.getenv("gemma_key"));
        var service = GenerativeAIDriverManager.create("google", "https://generativelanguage.googleapis.com", properties);


        System.out.println(service);
        var messages = new ChatMessage("user", "Hello, how are you?");
        var conversation = new ChatRequest("gemma", List.of(messages));
        var reply = service.chat(conversation);
        System.out.println(reply.message());
    }
}
