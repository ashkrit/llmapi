package org.llm.openai.param;

import org.llm.openai.model.ChatRequest;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

public class ParamPreparedRequest {


    public static ChatRequest prepare(ChatRequest conversation, Map<String, Object> params) {
        var messages = conversation.messages();
        var lastChatMessage = messages.get(messages.size() - 1);

        var messageContent = lastChatMessage.content();

        // Extract param names between {{ }}
        var paramPattern = Pattern.compile("\\{\\{([^}]+)}}");
        var matcher = paramPattern.matcher(messageContent);

        // Find missing parameters
        var missingParams = new ArrayList<String>();
        while (matcher.find()) {
            String paramName = matcher.group(1);
            if (!params.containsKey(paramName)) {
                missingParams.add(paramName);
            }
        }

        // Throw exception if any parameters are missing
        if (!missingParams.isEmpty()) {
            throw new IllegalArgumentException("Missing required parameters: " + String.join(", ", missingParams));
        }


        //iterate all the params from "params" and replace the placeholders in the last message
        for (var entry : params.entrySet()) {
            messageContent = messageContent.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
        }

        //Check for occurrences of {{ and }} in the message and get the field names and throw exception
        //extract each param name from the message

        var headMessages = conversation.messages().subList(0, conversation.messages().size() - 1);
        var newMessages = new ArrayList<>(headMessages);
        newMessages.add(new ChatRequest.ChatMessage(lastChatMessage.role(), messageContent));


        return new ChatRequest(conversation.model(), conversation.temperature(), newMessages);
    }
}
