package com.example.hacktj.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class problemBuilder extends Builder {
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
    
    public problemBuilder(Word word) {
        this.word = word;
    }

    @Override
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