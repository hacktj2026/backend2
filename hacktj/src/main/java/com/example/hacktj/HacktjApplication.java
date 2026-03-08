package com.example.hacktj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
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
    
    // Parse the problem JSON
    ObjectMapper mapper = new ObjectMapper();
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
    boolean correct = request.getSelected().equals(request.getCorrectAnswer());
    wordService.recordAnswer(request.getWordId(), correct);
    return new AnswerResponse(correct, request.getCorrectAnswer());
  }

  // Response DTOs for cleaner JSON serialization
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
    public boolean correct;
    public String correctAnswer;

    public AnswerResponse(boolean correct, String correctAnswer) {
      this.correct = correct;
    }
  }

  public static class AnswerRequest {
    public String wordId;
    public String selected;
    public String correctAnswer;

    public String getWordId() { return wordId; }
    public String getSelected() { return selected; }
    public String getCorrectAnswer() { return correctAnswer; }
  }
}