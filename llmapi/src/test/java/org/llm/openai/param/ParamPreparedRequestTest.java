package org.llm.openai.param;

import org.junit.jupiter.api.Test;
import org.llm.openai.model.ChatRequest;
import org.llm.openai.model.ChatRequest.ChatMessage;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParamPreparedRequestTest {

    @Test
    void testValidParameterReplacement() {
        // Given
        var messages = List.of(new ChatMessage("user", "Hello {{name}}, age is {{age}}"));
        var request = ChatRequest.create("gpt-4", messages);
        Map<String,Object> params = Map.of(
                "name", "John",
                "age", 25
        );

        // When
        var result = ParamPreparedRequest.prepare(request, params);

        // Then
        assertEquals("Hello John, age is 25",
                result.messages().get(0).content());
    }

    @Test
    void testMissingParameters() {
        // Given
        var messages = List.of(new ChatMessage("user", "Hello {{name}}, age is {{age}}"));
        var request = ChatRequest.create("gpt-4", messages);
        Map<String,Object> params = Map.of("name", "John");

        // When/Then
        var exception = assertThrows(IllegalArgumentException.class,
                () -> ParamPreparedRequest.prepare(request, params));
        assertEquals("Missing required parameters: age", exception.getMessage());
    }

    @Test
    void testMultipleMessagePreservation() {
        // Given
        var messages = List.of(
                new ChatMessage("system", "You are a helpful assistant"),
                new ChatMessage("user", "Hello {{name}}")
        );
        var request = ChatRequest.create("gpt-4", messages);
        Map<String,Object> params = Map.of("name", "John");

        // When
        var result = ParamPreparedRequest.prepare(request, params);

        // Then
        assertEquals(2, result.messages().size());
        assertEquals("You are a helpful assistant",
                result.messages().get(0).content());
        assertEquals("Hello John",
                result.messages().get(1).content());
    }

    @Test
    void testNoParameters() {
        // Given
        var messages = List.of(new ChatMessage("user", "Hello world"));
        var request = ChatRequest.create("gpt-4", messages);
        Map<String,Object> params = Map.of();

        // When
        var result = ParamPreparedRequest.prepare(request, params);

        // Then
        assertEquals("Hello world",
                result.messages().get(0).content());
    }
}