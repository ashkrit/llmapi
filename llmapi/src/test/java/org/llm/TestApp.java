package org.llm;

import com.google.gson.Gson;
import org.rpc.http.XBody;
import org.rpc.http.XHeader;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.service.RpcBuilder;
import org.rpc.service.RpcReply;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TestApp {
    public static void main(String[] args) throws Exception {


        Map<String, String> headers = httpSession();


        System.out.println(headers);

        RpcBuilder builder = new RpcBuilder().serviceUrl("https://www.meta.ai/api");
        MetaAI metaAI = builder.create(MetaAI.class);

        String cookie = String.format("_js_datr=%s; abra_csrf=%s; datr=%s;", headers.get("_js_datr"),
                headers.get("abra_csrf"), headers.get("datr"));
        //String csrf = String.format("%s; datr=%s;", headers.get("abra_csrf"), headers.get("datr"));


        Map<String, String> value = new HashMap<>();
        value.put("content-type", "application/x-www-form-urlencoded");
        value.put("sec-fetch-site", "same-origin");
        value.put("x-fb-friendly-name", "useAbraAcceptTOSForTempUserMutation");
        value.put("cookie", cookie);
        value.put("x-fb-lsd", "AVrWIDJrQQI");
        //value.put("abra_csrf", csrf);

        Map<String, Object> variables = new HashMap<>();
        variables.put("dob", "1999-01-01");
        variables.put("icebreaker_type", "TEXT");
        variables.put("__relay_internal__pv__WebPixelRatiorelayprovider", 1);

        Map<String, Object> payload = new HashMap<>();
        payload.put("lsd", "AVrWIDJrQQI");
        payload.put("fb_api_caller_class", "RelayModern");
        payload.put("fb_api_req_friendly_name", "useAbraAcceptTOSForTempUserMutation");
        payload.put("doc_id", "7604648749596940");
        payload.put("variables", variables);

        String text = new Gson().toJson(payload);
        String encValue = URLEncoder.encode(text, "UTF-8");

        System.out.println(encValue);


        URL u = new URL("https://www.meta.ai/api/graphql/");
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        value.forEach(connection::setRequestProperty);
        connection.getOutputStream().write(text.getBytes());

        log(connection.getInputStream());

        log(connection.getErrorStream());


        /*RpcReply<Map<String, Object>> reply = metaAI.token(cookie, csrf, payload);
        reply.execute();

        System.out.println(reply);*/

    }

    private static void log(InputStream in) throws IOException {
        int ch = -1;
        while ((ch = in.read()) != -1) {
            System.out.print((char) ch);
        }
    }

    private static Map<String, String> httpSession() throws IOException {
        URL u = new URL("https://www.meta.ai/");
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        InputStream in = connection.getInputStream();
        StringBuilder sb = new StringBuilder();
        int x = -1;
        while ((x = in.read()) != -1) {
            sb.append((char) x);
        }

        //System.out.println(sb);

        Map<String, String[]> keys = new HashMap<>();
        keys.put("_js_datr", new String[]{"_js_datr\":{\"value\":\"", "\","});
        keys.put("abra_csrf", new String[]{"abra_csrf\":{\"value\":\"", "\","});
        keys.put("datr", new String[]{"datr\":{\"value\":\"", "\","});
        keys.put("lsd", new String[]{"\"LSD\",[],{\"token\":\"", "\"}"});

        Map<String, String> headers = keys.entrySet()
                .stream()
                .map(k -> new String[]{k.getKey(), extractValue(sb.toString(), k.getValue()[0], k.getValue()[1])})
                .collect(Collectors.toMap(v -> v[0], v -> v[1]));
        return headers;
    }

    public static String extractValue(String text, String startStr, String endStr) {
        int start = text.indexOf(startStr) + startStr.length();
        int end = text.indexOf(endStr, start);
        return text.substring(start, end);
    }


    interface MetaAI {

        @XPOST("/graphql")
        @XHeaders({
                "Content-Type: application/x-www-form-urlencoded",
                "sec-fetch-site: same-origin",
                "x-fb-friendly-name: useAbraAcceptTOSForTempUserMutation"})
        RpcReply<Map<String, Object>> token(@XHeader("cookie") String cookie, @XHeader("abra_csrf") String csrf, @XBody Map<String, Object> payload);

    }
}
