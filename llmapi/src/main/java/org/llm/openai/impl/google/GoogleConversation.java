package org.llm.openai.impl.google;

import java.util.ArrayList;
import java.util.List;

public class GoogleConversation {
    public List<Content> contents = new ArrayList<>();

    public static class Content {
        public String role;
        public List<Part> parts = new ArrayList<>();
    }

    public static class Part {
        public String text;


        public static Part create(String text) {
            var part = new Part();
            part.text = text;
            return part;
        }


    }

    public void append(String text) {

        contents.add(new Content());
        var content = contents.get(contents.size() - 1);
        content.parts.add(Part.create(text));
    }

}
