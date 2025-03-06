package org.llm.openai;

import org.llm.openai.model.internal.Conversation;
import org.llm.openai.model.internal.OpenAIConversationReply;
import org.llm.openai.model.internal.OpenAIEmbedding;
import org.llm.openai.model.internal.OpenAIEmbedding.OpenAIEmbeddingReply;
import org.rpc.http.XBody;
import org.rpc.http.XHeader;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.service.RpcReply;

public interface OpenAIService {


    @XPOST("/v1/embeddings")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<OpenAIEmbeddingReply> embedding(@XHeader("Authorization") String apiKey, @XBody OpenAIEmbedding embedding);


    @XPOST("/v1/chat/completions")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<OpenAIConversationReply> chat(@XHeader("Authorization") String apiKey, @XBody Conversation conversation);


}
