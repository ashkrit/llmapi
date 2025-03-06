package org.llm;

import org.llm.openai.impl.anthropic.AnthropicService;
import org.llm.openai.impl.google.GoogleService;
import org.llm.openai.impl.groq.GroqService;
import org.llm.openai.impl.ollama.OllamaService;
import org.llm.openai.model.*;
import org.llm.openai.impl.openai.OpenAIService;
import org.llm.openai.impl.openai.OpenAIConversationRequest;
import org.llm.openai.impl.google.GoogleConversation;

import org.rpc.service.RpcBuilder;

import java.util.Arrays;

/**
 * Hello world!
 */
public class App {

    static String gptApiKey = "Bearer " + System.getenv("gpt_key");
    static String anthropicApiKey = System.getenv("ANTHROPIC_API_KEY");
    static String groqApiKey = "Bearer " + System.getenv("gorq_key");
    static String googleAPiKey = System.getenv("gemma_key");

    public static void main(String[] args) {


        /*
        openAi();
        _anthropicChat();
        _groqChat();
        _googleChat();
        */

        ollama();
    }

    private static void openAi() {
        var service = new RpcBuilder().serviceUrl("https://api.openai.com/").create(OpenAIService.class);

        var message = new OpenAIConversationRequest("gpt-4o-mini");
        message.append("user", "Say this is test");
        var replyMessage = service.chat(gptApiKey, message);
        replyMessage.execute();

        if (replyMessage.isSuccess()) {
            var value = (ChatMessageReply) replyMessage.value();
            System.out.println(value.message());
        } else {
            System.out.println(replyMessage.exception());
            System.out.println(replyMessage.error());
        }


        var openAIEmbedding = new EmbeddingRequest("text-embedding-3-small", "How are you");
        var reply = service.embedding(gptApiKey, openAIEmbedding);

        reply.execute();

        if (reply.isSuccess()) {
            var value = (EmbeddingReply) reply.value();
            System.out.println(Arrays.toString(value.embedding()));
        } else {
            System.out.println(reply.exception());
            System.out.println(reply.error());
        }

    }

    private static void _anthropicChat() {
        var anthropicService = new RpcBuilder().serviceUrl("https://api.anthropic.com").create(AnthropicService.class);

        var message = new OpenAIConversationRequest("claude-3-7-sonnet-20250219");
        message.append("user", "Say this is test");
        var replyMessage = anthropicService.chat(anthropicApiKey, message);
        replyMessage.execute();

        if (replyMessage.isSuccess()) {
            var value = (ChatMessageReply) replyMessage.value();
            System.out.println(value.message());
        } else {
            System.out.println(replyMessage.exception());
            System.out.println(replyMessage.error());
        }
    }


    private static void _groqChat() {
        var service = new RpcBuilder().serviceUrl("https://api.groq.com/openai").create(GroqService.class);

        var message = new OpenAIConversationRequest("llama-3.3-70b-versatile");
        message.append("user", "Say this is test");
        var replyMessage = service.chat(groqApiKey, message);
        replyMessage.execute();

        if (replyMessage.isSuccess()) {
            var value = (ChatMessageReply) replyMessage.value();
            System.out.println(value.message());
        } else {
            System.out.println(replyMessage.exception());
            System.out.println(replyMessage.error());
        }
    }


    private static void _googleChat() {
        var service = new RpcBuilder().serviceUrl("https://generativelanguage.googleapis.com").create(GoogleService.class);

        var message = new GoogleConversation();
        message.append("Say this is test");
        var replyMessage = service.chat(googleAPiKey, message);
        replyMessage.execute();

        if (replyMessage.isSuccess()) {
            var value = (ChatMessageReply) replyMessage.value();
            System.out.println(value.message());
        } else {
            System.out.println(replyMessage.exception());
            System.out.println(replyMessage.error());
        }
    }


    private static void ollama() {
        var service = new RpcBuilder().serviceUrl("http://localhost:11434").create(OllamaService.class);

        var message = new OpenAIConversationRequest("llama3.2");
        message.append("user", "Say this is test");
        var replyMessage = service.chat(message);
        replyMessage.execute();

        if (replyMessage.isSuccess()) {
            var value = (ChatMessageReply) replyMessage.value();
            System.out.println(value.message());
        } else {
            System.out.println(replyMessage.exception());
            System.out.println(replyMessage.error());
        }


        var openAIEmbedding = new EmbeddingRequest("all-minilm", "How are you");
        var reply = service.embedding(openAIEmbedding);

        reply.execute();

        if (reply.isSuccess()) {
            var value = (EmbeddingReply) reply.value();
            System.out.println(Arrays.toString(value.embedding()));
        } else {
            System.out.println(reply.exception());
            System.out.println(reply.error());
        }

    }


}
