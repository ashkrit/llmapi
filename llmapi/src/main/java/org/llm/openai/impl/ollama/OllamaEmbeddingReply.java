package org.llm.openai.impl.ollama;

import org.llm.openai.model.EmbeddingReply;

import java.util.List;


public class OllamaEmbeddingReply implements EmbeddingReply {

    public List<float[]> embeddings;

    @Override
    public float[] embedding() {
        var last = embeddings.get(embeddings.size() - 1);
        return last;
    }

}
