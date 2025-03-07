package org.llm.openai.parser;

import org.junit.jupiter.api.Test;
import org.llm.openai.model.ChatMessageReply;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageJsonParserTest {

    record TestData(String name, int age) {}

    @Test
    void testParseValidJsonMarkdown() {
        // Given
        var reply = new SampleChatMessage("```json\n{\"name\":\"John\",\"age\":25}\n```");
        var failedParsing = new StringBuilder();

        // When
        var result = ChatMessageJsonParser.parse(reply, TestData.class,
                (json, ex) -> failedParsing.append(json));

        // Then
        assertTrue(result.isPresent());
        assertEquals("John", result.get().name());
        assertEquals(25, result.get().age());
        assertTrue(failedParsing.isEmpty());
    }

    @Test
    void testParseValidJsonWithoutMarkdown() {
        // Given
        var reply = new SampleChatMessage("{\"name\":\"John\",\"age\":25}");
        var failedParsing = new StringBuilder();

        // When
        var result = ChatMessageJsonParser.parse(reply, TestData.class,
                (json, ex) -> failedParsing.append(json));

        // Then
        assertTrue(result.isPresent());
        assertEquals("John", result.get().name());
        assertEquals(25, result.get().age());
        assertTrue(failedParsing.isEmpty());
    }

    @Test
    void testParseInvalidJson() {
        // Given
        var reply = new SampleChatMessage("Invalid JSON data");
        var failedParsing = new StringBuilder();

        // When
        var result = ChatMessageJsonParser.parse(reply, TestData.class,
                (json, ex) -> failedParsing.append(json));

        // Then
        assertFalse(result.isPresent());
        assertEquals("Invalid JSON data", failedParsing.toString());
    }

    @Test
    void testParseNullMessage() {
        // Given
        var reply = new SampleChatMessage(null);
        var failedParsing = new StringBuilder();

        // When
        var result = ChatMessageJsonParser.parse(reply, TestData.class,
                (json, ex) -> failedParsing.append(json));

        // Then
        assertFalse(result.isPresent());
        assertEquals("", failedParsing.toString());
    }

    public record SampleChatMessage(String message) implements ChatMessageReply {

    }
}