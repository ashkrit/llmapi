package org.llm.openai.impl.anthropic;

import org.llm.openai.model.ChatMessageReply;

import java.util.List;

public class AnthropicMessageReply implements ChatMessageReply {

    //list of Message as 'content'
    public List<Message> content;

    @Override
    public String message() {
        var last = content.get(content.size() - 1);
        return last.text;
    }

    public static class Message {
        //type and text
        public String type;
        public String text;
    }
}
