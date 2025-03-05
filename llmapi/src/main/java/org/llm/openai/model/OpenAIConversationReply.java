package org.llm.openai.model;

import java.util.List;

public class OpenAIConversationReply {

    public final List<ReplyMessage> choices;

    public OpenAIConversationReply(List<ReplyMessage> choices) {
        this.choices = choices;
    }


    public static class ReplyMessage {
        public final int index;
        public final Conversation.Message message;

        public ReplyMessage(int index, Conversation.Message message) {
            this.index = index;
            this.message = message;
        }
    }

}
