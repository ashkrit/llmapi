package org.llm;

import org.llm.openai.AnthropicService;
import org.llm.openai.GoogleService;
import org.llm.openai.GroqService;
import org.llm.openai.model.*;
import org.llm.openai.openai.OpenAIService;
import org.llm.openai.model.internal.Conversation;
import org.llm.openai.model.internal.GoogleConversation;

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


        var service = new RpcBuilder()
                .serviceUrl("https://api.openai.com/")
                .create(OpenAIService.class);

        _embeddings(service);
        _chat(service);

        _anthropicChat();
        _groqChat();
        _googleChat();

    }

    private static void _anthropicChat() {
        var anthropicService = new RpcBuilder()
                .serviceUrl("https://api.anthropic.com")
                .create(AnthropicService.class);

        var message = new Conversation("claude-3-7-sonnet-20250219");
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
        var service = new RpcBuilder()
                .serviceUrl("https://api.groq.com/openai")
                .create(GroqService.class);

        var message = new Conversation("llama-3.3-70b-versatile");
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
        var service = new RpcBuilder()
                .serviceUrl("https://generativelanguage.googleapis.com")
                .create(GoogleService.class);

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


    private static void _chat(OpenAIService service) {

        var message = new Conversation("gpt-4o-mini");
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
    }

    private static void _embeddings(OpenAIService service) {
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
}
