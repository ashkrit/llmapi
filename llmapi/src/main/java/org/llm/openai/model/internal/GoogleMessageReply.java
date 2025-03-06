package org.llm.openai.model.internal;

import org.llm.openai.model.ChatMessageReply;

import java.util.ArrayList;
import java.util.List;

public class GoogleMessageReply implements ChatMessageReply {
    public List<ReplyContent> candidates = new ArrayList<>();

    @Override
    public String message() {
        var last = candidates.get(candidates.size() - 1);
        var lastPart = last.content.parts.get(last.content.parts.size() - 1);
        return lastPart.text;
    }

    public static class ReplyContent {
        public Parts content;
    }

    public static class Parts {
        public List<ReplyPart> parts = new ArrayList<>();
    }

    public static class ReplyPart {
        public String text;

    }


}
