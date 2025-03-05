package org.llm.openai.model;

import java.util.ArrayList;
import java.util.List;

public class GoogleMessageReply {
    public List<ReplyContent> candidates = new ArrayList<>();

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
