package com.example.hacktj;

import com.example.hacktj.model.Word;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class problemBuilder {
    Word word;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static JsonNode vocabData;

    static {
        try {
            // Place spanish_vocab.json in src/main/resources/
            InputStream is = problemBuilder.class
                    .getClassLoader()
                    .getResourceAsStream("spv.json");
            vocabData = mapper.readTree(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load vocab JSON", e);
        }
    }

    public problemBuilder(Word word) {
        this.word = word;
    }

    public String problem() throws Exception {
        String difficulty = word.getSkillLevel();  // e.g. "A1"
        String wordType = word.getWordType();       // e.g. "nouns"

        // Get all words of the same difficulty + type from JSON
        List<String> pool = new ArrayList<>();
        JsonNode typeNode = vocabData.path(difficulty).path(wordType);

        for (JsonNode entry : typeNode) {
            String spanish = entry.path("spanish").asText();
            // Don't add the correct word to the wrong answer pool
            if (!spanish.equals(word.getWord())) {
                pool.add(spanish);
            }
        }

        // Shuffle and pick up to 3 wrong answers
        Collections.shuffle(pool);
        List<String> wrongAnswers = pool.subList(0, Math.min(3, pool.size()));

        // Build choices with correct answer mixed in
        List<String> choices = new ArrayList<>(wrongAnswers);
        choices.add(word.getWord());
        Collections.shuffle(choices);

        // Build JSON
        ObjectNode problem = mapper.createObjectNode();
        problem.put("question", "What is the meaning of: " + word.getWord() + "?");
        problem.put("correctAnswer", word.getWord());
        problem.put("skillLevel", difficulty);
        problem.put("wordType", wordType);

        ArrayNode choicesNode = mapper.createArrayNode();
        choices.forEach(choicesNode::add);
        problem.set("choices", choicesNode);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(problem);
    }
}