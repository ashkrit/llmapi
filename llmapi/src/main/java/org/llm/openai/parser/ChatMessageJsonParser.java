package org.llm.openai.parser;

import com.google.gson.Gson;
import org.llm.openai.model.ChatMessageReply;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public class ChatMessageJsonParser {

    public static <T> Optional<T> parse(ChatMessageReply reply, Class<T> returnType, BiConsumer<String, Exception> onFailedParsing) {
        var message = reply.message().trim();

        // Pattern matches text between ```json and ``` markers
        var pattern = Pattern.compile("^```json\\s*(.+?)\\s*```$", Pattern.DOTALL);
        var matcher = pattern.matcher(message);

        var jsonContent = matcher.matches()
                ? matcher.group(1)  // Extract content between markers
                : message;         // Use full message if no markers

        try {
            return Optional.ofNullable(new Gson().fromJson(jsonContent, returnType));
        } catch (Exception e) {
            onFailedParsing.accept(jsonContent, e);
            return Optional.empty();
        }
    }
}
