package org.llm.openai;

import com.google.gson.Gson;
import org.llm.openai.model.ChatMessageReply;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.model.EmbeddingReply;
import org.llm.openai.model.EmbeddingRequest;

import java.util.regex.Pattern;

public interface GenerativeAIService {
    ChatMessageReply chat(ChatRequest conversation);

    default EmbeddingReply embedding(EmbeddingRequest embedding) {
        throw new UnsupportedOperationException("Not Supported");
    }

    default <T> T chat(ChatRequest conversation, Class<T> returnType) {
        var reply = chat(conversation);
        var message = reply.message();

        // Pattern matches text between ```json and ``` markers
        var pattern = Pattern.compile("^```json\\s*(.+?)\\s*```$", Pattern.DOTALL);
        var matcher = pattern.matcher(message);

        var jsonContent = matcher.matches()
                ? matcher.group(1)  // Extract content between markers
                : message;         // Use full message if no markers

        return new Gson().fromJson(jsonContent, returnType);
    }
}
