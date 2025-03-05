package org.llm.openai.model;

import java.util.List;

public class AnthropicMessageReply {

    //list of Message as 'content'
    public List<Message> content;

    public static class Message {
        //type and text
        public String type;
        public String text;
    }
}
