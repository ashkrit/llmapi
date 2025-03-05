package org.llm.openai.model;

import com.google.gson.Gson;

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

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
