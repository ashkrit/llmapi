package org.llm;

import org.llm.openai.AnthropicService;
import org.llm.openai.model.Conversation;
import org.llm.openai.model.OpenAIEmbedding;
import org.llm.openai.OpenAIService;
import org.rpc.service.RpcBuilder;

import java.util.Arrays;

/**
 * Hello world!
 */
public class App {

    static String gptApiKey = "Bearer " + System.getenv("gpt_key");
    static String anthropicApiKey =  System.getenv("ANTHROPIC_API_KEY");

    public static void main(String[] args) {


        var service = new RpcBuilder()
                .serviceUrl("https://api.openai.com/")
                .create(OpenAIService.class);

        //_embeddings(service);
        //_chat(service);

        _anthropicChat();

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
            System.out.println(replyMessage.value());
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
            System.out.println(replyMessage.value().choices.get(0).message);
        } else {
            System.out.println(replyMessage.exception());
            System.out.println(replyMessage.error());
        }
    }

    private static void _embeddings(OpenAIService service) {
        var openAIEmbedding = new OpenAIEmbedding("text-embedding-3-small", "How are you");
        var reply = service.embedding(gptApiKey, openAIEmbedding);

        reply.execute();

        if (reply.isSuccess()) {
            System.out.println(Arrays.toString(reply.value().data.get(0).embedding));
        } else {
            System.out.println(reply.exception());
            System.out.println(reply.error());
        }

    }
}
