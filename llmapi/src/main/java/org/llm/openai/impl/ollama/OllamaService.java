package org.llm.openai.impl.ollama;

import org.llm.openai.impl.openai.OpenAIConversationRequest;
import org.llm.openai.model.EmbeddingRequest;
import org.rpc.http.XBody;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.service.RpcReply;

public interface OllamaService {


    @XPOST("/api/chat")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<OllamaMessageReply> chat(@XBody OpenAIConversationRequest conversation);

    @XPOST("/api/embed")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<OllamaEmbeddingReply> embedding(@XBody EmbeddingRequest embedding);


}
