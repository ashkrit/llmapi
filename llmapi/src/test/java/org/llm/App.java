package org.llm;

import org.llm.openai.model.Conversation;
import org.llm.openai.model.OpenAIEmbedding;
import org.llm.openai.OpenAIService;
import org.rpc.service.RpcBuilder;

import java.util.Arrays;

/**
 * Hello world!
 */
public class App {

    static String apiKey = "Bearer " + System.getenv("gpt_key");

    public static void main(String[] args) {

        var builder = new RpcBuilder().serviceUrl("https://api.openai.com/");
        var service = builder.create(OpenAIService.class);

        _embeddings(service);
        _chat(service);

    }

    private static void _chat(OpenAIService service) {

        var message = new Conversation("gpt-4o-mini");
        message.append("user", "Say this is test");
        var replyMessage = service.chat(apiKey, message);
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
        var reply = service.embedding(apiKey, openAIEmbedding);

        reply.execute();

        if (reply.isSuccess()) {
            System.out.println(Arrays.toString(reply.value().data.get(0).embedding));
        } else {
            System.out.println(reply.exception());
            System.out.println(reply.error());
        }

    }
}
