package org.llm.openai;

import org.llm.openai.model.internal.GoogleConversation;
import org.llm.openai.model.internal.GoogleMessageReply;
import org.rpc.http.XBody;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.http.XQuery;
import org.rpc.service.RpcReply;

public interface GoogleService {

    @XPOST("/v1beta/models/gemini-2.0-flash:generateContent")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<GoogleMessageReply> chat(@XQuery("key") String apiKey, @XBody GoogleConversation conversation);
}
