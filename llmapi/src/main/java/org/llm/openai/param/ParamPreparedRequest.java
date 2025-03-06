package org.llm.openai.param;

import org.llm.openai.model.ChatRequest;
import org.llm.openai.model.ChatRequest.ChatMessage;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

public class ParamPreparedRequest {


    public static ChatRequest prepare(ChatRequest conversation, Map<String, Object> params) {
        var messages = conversation.messages();
        var lastChatMessage = messages.get(messages.size() - 1);

        var messageContent = lastChatMessage.content();
        _validateParams(params, messageContent);
        messageContent = _fillParams(params, messageContent);

        //extract each param name from the message
        var newMessages = _mergeMessage(conversation, lastChatMessage, messageContent);

        return new ChatRequest(conversation.model(), conversation.temperature(), newMessages);
    }

    private static void _validateParams(Map<String, Object> params, String messageContent) {
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
    }

    private static String _fillParams(Map<String, Object> params, String messageContent) {
        //iterate all the params from "params" and replace the placeholders in the last message
        for (var entry : params.entrySet()) {
            messageContent = messageContent.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
        }
        return messageContent;
    }

    private static ArrayList<ChatMessage> _mergeMessage(ChatRequest conversation, ChatMessage lastChatMessage, String messageContent) {
        var headMessages = conversation.messages().subList(0, conversation.messages().size() - 1);
        var newMessages = new ArrayList<>(headMessages);
        newMessages.add(new ChatMessage(lastChatMessage.role(), messageContent));
        return newMessages;
    }
}
