package org.llm.openai.anthropic;

import org.llm.openai.model.internal.AnthropicMessageReply;
import org.llm.openai.model.internal.Conversation;
import org.rpc.http.XBody;
import org.rpc.http.XHeader;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.service.RpcReply;

public interface AnthropicService {


    @XPOST("/v1/messages")
    @XHeaders({"Content-Type: application/json", "anthropic-version: 2023-06-01"})
    RpcReply<AnthropicMessageReply> chat(@XHeader("x-api-key") String apiKey, @XBody Conversation conversation);


}
