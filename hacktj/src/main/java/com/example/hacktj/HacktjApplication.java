package com.example.hacktj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hacktj.model.User;
import com.example.hacktj.model.Word;
import com.example.hacktj.repository.WordRepository;
import com.example.hacktj.service.WordService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HacktjApplication {
  @Autowired
  WordService wordService;
  @Autowired
  WordRepository wordRepository;
  public static void main(String[] args) {
    SpringApplication.run(HacktjApplication.class, args);
  }

  // Get a new problem for a given level
  @GetMapping("/problem")
  public ProblemResponse getProblem(@RequestParam(value = "level", defaultValue = "1") int level) throws Exception {
    Word word = wordService.getNext(level);
    problemBuilder builder = new problemBuilder(word);
    String problemJson = builder.problem();
    
    // Parse the problem JSON
    ObjectMapper mapper = new ObjectMapper();
    var problemData = mapper.readTree(problemJson);
    
    return new ProblemResponse(
      word.getId(),
      problemData.get("question").asText(),
      problemData.get("correctAnswer").asText(),
      mapper.convertValue(problemData.get("choices"), String[].class),
      word.getSkillLevel()
    );
  }

  // Check if the answer is correct
  @PostMapping("/check-answer")
  public AnswerResponse checkAnswer(@RequestBody AnswerRequest request) {
    Word word = wordRepository.findByName(request.getWordName());
    boolean correct = word.getMeaning().equals(request.getSelected());
    wordService.rightOrWrong(correct, word.getLevel(), new User());
    return new AnswerResponse(correct);
  }

  // Response DTOs for cleaner JSON serialization
  public static class ProblemResponse {
    public String wordId;
    public String question;
    public String correctAnswer;
    public String[] choices;
    public String level;

    public ProblemResponse(String wordId, String question, String correctAnswer, String[] choices, String level) {
      this.wordId = wordId;
      this.question = question;
      this.correctAnswer = correctAnswer;
      this.choices = choices;
      this.level = level;
    }
  }

  public static class AnswerResponse {
    public boolean correct;
    public String correctAnswer;
    public AnswerResponse(boolean correct) {
      this.correct = correct;
    }
  }

  public static class AnswerRequest {
    public String wordName;
    public String selected;

    public String getWordName() { return wordName; }
    public String getSelected() { return selected; }
  }
}