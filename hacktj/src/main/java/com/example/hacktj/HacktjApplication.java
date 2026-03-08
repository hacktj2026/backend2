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

import com.example.hacktj.model.Word;
import com.example.hacktj.model.problemBuilder;
import com.example.hacktj.repository.UserRepository;
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
  @Autowired
  UserRepository userRepository;
  public static void main(String[] args) {
    SpringApplication.run(HacktjApplication.class, args);
  }

  @GetMapping("/problem")
  public ProblemResponse getProblem(@RequestParam(value = "level", defaultValue = "1") int level) throws Exception {
    Word word = wordService.getNext(level);
    if (word == null) {
      throw new Exception("No word found for level " + level);
    }
    ObjectMapper mapper = new ObjectMapper();
    
    problemBuilder builder = new problemBuilder(word);
    String problemJson = builder.problem();
    
    var problemData = mapper.readTree(problemJson);
    
    
    String[] choices = mapper.convertValue(problemData.get("choices"), String[].class);
    
    return new ProblemResponse(
      word.getId(),
      problemData.get("question").asText(),
      problemData.get("correctAnswer").asText(),
      choices,
      word.getSkillLevel()
    );
  }

  @PostMapping("/check-answer")
  public AnswerResponse checkAnswer(@RequestBody AnswerRequest request) {
    Word word = wordRepository.findByWord(request.getWordName());
    boolean correct = word.getMeaning().equals(request.getSelected());
    wordService.rightOrWrong(correct, word.getLevel(), userRepository.findByName(request.getUsername()));
    return new AnswerResponse(correct);
  }

  public static class ProblemResponse {
    public String wordId;
    public String question;
    public String correctAnswer;
    public String[] choices;
    public String level;

    public ProblemResponse() {}
    
    public ProblemResponse(String wordId, String question, String correctAnswer, String[] choices, String level) {
      this.wordId = wordId;
      this.question = question;
      this.correctAnswer = correctAnswer;
      this.choices = choices;
      this.level = level;
    }
  }

  public static class AnswerResponse {
    private boolean correct;
    public AnswerResponse(boolean correct) {
      this.correct = correct;
    }
  }

  public static class AnswerRequest {
    private String wordName;
    private String selected;
    private String username;
    public String getWordName() { return wordName; }
    public String getSelected() { return selected; }
    public String getUsername() { return username; }
  }
}