package org.llm.openai.model.internal;

import org.llm.openai.model.EmbeddingReply;

import java.util.List;

public class OpenAIEmbedding {
    public final String model;
    public final String input;


    public OpenAIEmbedding(String model, String input) {
        this.model = model;
        this.input = input;
    }


    public static class OpenAIEmbeddingReply implements EmbeddingReply {

        public final List<EmbeddingValue> data;

        public OpenAIEmbeddingReply(List<EmbeddingValue> data) {
            this.data = data;
        }

        @Override
        public float[] embedding() {
            var last = data.get(data.size() - 1);
            return last.embedding;
        }

        public static class EmbeddingValue {
            public final float[] embedding;

            public EmbeddingValue(float[] embedding) {
                this.embedding = embedding;
            }
        }
    }
}