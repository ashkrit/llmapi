package org.llm.openai.model.internal;

import com.google.gson.Gson;
import org.llm.openai.model.ChatMessageReply;

import java.util.List;

public class OpenAIConversationReply implements ChatMessageReply {

    public final List<ReplyMessage> choices;

    public OpenAIConversationReply(List<ReplyMessage> choices) {
        this.choices = choices;
    }

    @Override
    public String message() {
        var last = choices.get(choices.size() - 1);
        return last.message.content();

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
