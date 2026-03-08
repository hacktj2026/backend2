package com.example.hacktj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import com.example.hacktj.service.WordService;
import com.example.hacktj.model.Word;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HacktjApplication {
  @Autowired
  WordService wordService;
  public static void main(String[] args) {
    SpringApplication.run(HacktjApplication.class, args);
  }

  // Get a new problem for a given level
  @GetMapping("/problem")
  public ProblemResponse getProblem(@RequestParam(value = "level", defaultValue = "1") int level) throws Exception {
    Word word = wordService.getNext(level);
    if (word == null) {
      throw new Exception("No word found for level " + level);
    }
    
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

  // Check if the answer is correct
  @PostMapping("/check-answer")
  public AnswerResponse checkAnswer(@RequestBody AnswerRequest request) {
    if (request == null || request.wordId == null) {
      return new AnswerResponse(false);
    }
    
    boolean correct = request.selected != null && request.selected.equals(request.correctAnswer);
    wordService.recordAnswer(request.wordId, correct);
    return new AnswerResponse(correct);
  }

  // Response DTOs
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

    public AnswerResponse() {}
    
    public AnswerResponse(boolean correct) {
      this.correct = correct;
    }
  }

  public static class AnswerRequest {
    public String wordId;
    public String selected;
    public String correctAnswer;

    public AnswerRequest() {}
    
    public void setWordId(String wordId) { this.wordId = wordId; }
    public void setSelected(String selected) { this.selected = selected; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
  }
}