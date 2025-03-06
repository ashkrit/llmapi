package org.llm;

import org.llm.openai.GenerativeAIDriverManager;
import org.llm.openai.impl.anthropic.AnthropicAIFactory;
import org.llm.openai.impl.google.GoogleAIFactory;
import org.llm.openai.impl.groq.GroqFactory;
import org.llm.openai.impl.ollama.OllamaFactory;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.model.ChatRequest.ChatMessage;
import org.llm.openai.impl.openai.OpenAIFactory;
import org.llm.openai.model.EmbeddingRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.llm.openai.GenerativeAIDriverManager.registerService;

public class GenAiApp {

    public static void main(String[] args) {

        registerService(OpenAIFactory.NAME, new OpenAIFactory());
        registerService(AnthropicAIFactory.NAME, new AnthropicAIFactory());
        registerService(GoogleAIFactory.NAME, new GoogleAIFactory());
        registerService(GroqFactory.NAME, new GroqFactory());
        registerService(OllamaFactory.NAME, new OllamaFactory());

        _openAi();
        _anthropicAi();
        _googleAi();
        _groqAi();
        _ollamaAi();


    }

    private static void _openAi() {
        Map<String, Object> properties = Map.of("apiKey", System.getenv("gpt_key"));
        var service = GenerativeAIDriverManager.create(OpenAIFactory.NAME, "https://api.openai.com/", properties);


        System.out.println(service);
        var messages = new ChatMessage("user", "Hello, how are you?");
        var conversation = new ChatRequest("gpt-4o-mini", List.of(messages));
        var reply = service.chat(conversation);
        System.out.println(reply.message());

        var vector = service.embedding(new EmbeddingRequest("text-embedding-3-small", "How are you"));
        System.out.println(Arrays.toString(vector.embedding()));
    }


    private static void _ollamaAi() {
        Map<String, Object> properties = Map.of();
        var service = GenerativeAIDriverManager.create(OllamaFactory.NAME, "http://localhost:11434", properties);


        System.out.println(service);
        var messages = new ChatMessage("user", "Hello, how are you?");
        var conversation = new ChatRequest("llama3.2", List.of(messages));
        var reply = service.chat(conversation);
        System.out.println(reply.message());

        var vector = service.embedding(new EmbeddingRequest("all-minilm", "How are you"));
        System.out.println(Arrays.toString(vector.embedding()));
    }

    private static void _anthropicAi() {
        Map<String, Object> properties = Map.of("apiKey", System.getenv("ANTHROPIC_API_KEY"));
        var service = GenerativeAIDriverManager.create(AnthropicAIFactory.NAME, "https://api.anthropic.com", properties);


        System.out.println(service);
        var messages = new ChatMessage("user", "Hello, how are you?");
        var conversation = new ChatRequest("claude-3-7-sonnet-20250219", List.of(messages));
        var reply = service.chat(conversation);
        System.out.println(reply.message());
    }


    private static void _googleAi() {
        Map<String, Object> properties = Map.of("apiKey", System.getenv("gemma_key"));
        var service = GenerativeAIDriverManager.create(GoogleAIFactory.NAME, "https://generativelanguage.googleapis.com", properties);


        System.out.println(service);
        var messages = new ChatMessage("user", "Hello, how are you?");
        var conversation = new ChatRequest("gemma", List.of(messages));
        var reply = service.chat(conversation);
        System.out.println(reply.message());
    }

    private static void _groqAi() {
        Map<String, Object> properties = Map.of("apiKey", System.getenv("gorq_key"));
        var service = GenerativeAIDriverManager.create(GroqFactory.NAME, "https://api.groq.com/openai", properties);


        System.out.println(service);
        var messages = new ChatMessage("user", "Hello, how are you?");
        var conversation = new ChatRequest("llama-3.3-70b-versatile", List.of(messages));
        var reply = service.chat(conversation);
        System.out.println(reply.message());
    }
}
