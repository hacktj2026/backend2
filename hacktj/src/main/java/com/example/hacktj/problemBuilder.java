package com.example.hacktj;

import com.example.hacktj.model.Word;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.*;

public class problemBuilder {
    Word word;
    boolean reversed = Math.random() < 0.5;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static List<Word>[] vocabData = new ArrayList[6];
    static {
        try {
            for(int a = 0; a < vocabData.length; a++)
                vocabData[a] = new ArrayList<>();
            Scanner scan = new Scanner(problemBuilder.class.getClassLoader().getResourceAsStream("vocabulary.txt"));
            while(scan.hasNextLine()) {
                String[] sarr = scan.nextLine().split(" ");
                System.out.println(sarr[0]);
                vocabData[changeDiffLevel(sarr[2])].add(new Word(sarr[0], sarr[2], sarr[1], sarr[3], 1));
            }
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
    }
    private static int changeDiffLevel(String s) {
        System.out.println(s);
        switch(s) {
            case "A1": return 0;
            case "A2": return 1;
            case "B1": return 2;
            case "B2": return 3;
            case "C1": return 4;
            case "C2": return 5;
            default: return -1;
        }
    }
    public problemBuilder(Word word) {
        this.word = word;
    }

    public String problem() throws Exception {
        String difficulty = word.getSkillLevel();
        String wordType = word.getWordType();

        List<String> pool = new ArrayList<>();

        for (Word w : vocabData[changeDiffLevel(difficulty)]) {
            if (!w.getWord().equals(word.getWord()) && w.getWordType().equals(wordType)) {
                pool.add(w.getMeaning());
            }
        }
        Collections.shuffle(pool);
        List<String> wrongAnswers = pool.subList(0, Math.min(3, pool.size()));

        List<String> choices = new ArrayList<>(wrongAnswers);
        if(reversed) {
            choices.add(word.getWord());
        } else {
            choices.add(word.getMeaning());
        }
        Collections.shuffle(choices);

        ObjectNode problem = mapper.createObjectNode();
        if (reversed) {
            problem.put("question", "How do you say in Spanish: " + word.getMeaning() + "?");
            problem.put("correctAnswer", word.getWord());
        } else {
            problem.put("question", "What is the meaning of: " + word.getWord() + "?");
            problem.put("correctAnswer", word.getMeaning());
        }

        problem.put("skillLevel", difficulty);
        problem.put("wordType", wordType);

        ArrayNode choicesNode = mapper.createArrayNode();
        choices.forEach(choicesNode::add);
        problem.set("choices", choicesNode);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(problem);
    }
}