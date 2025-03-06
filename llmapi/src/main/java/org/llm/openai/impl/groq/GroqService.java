package org.llm.openai.impl.groq;

import org.llm.openai.impl.openai.OpenAIConversationRequest;
import org.llm.openai.impl.openai.OpenAIConversationReply;
import org.rpc.http.XBody;
import org.rpc.http.XHeader;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.service.RpcReply;


public interface GroqService {

    @XPOST("/v1/chat/completions")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<OpenAIConversationReply> chat(@XHeader("Authorization") String apiKey, @XBody OpenAIConversationRequest conversation);
}