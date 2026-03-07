package com.example.hacktj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import com.example.hacktj.repository.WordRepository;
import com.example.hacktj.service.WordService;
import com.example.hacktj.model.Word;

@SpringBootApplication
@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*")
public class HacktjApplication {
  @Autowired
  WordRepository wordRepository;
  @Autowired
  WordService wordService;

  public static void main(String[] args) {
    SpringApplication.run(HacktjApplication.class, args);
  }

  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("Hello %s!", name);
  }

  // Frontend calls this to get a new problem
  @GetMapping("/next")
  public String nextProblem(@RequestParam(value = "level", defaultValue = "1") int level) throws Exception {
    Word word = wordService.getNext(level);
    problemBuilder builder = new problemBuilder(word);
    return builder.problem();
  }

  // Frontend calls this with the user's answer
  @PostMapping("/answer")
  public String checkAnswer(@RequestBody AnswerRequest request) {
    boolean correct = request.getSelected().equals(request.getCorrectAnswer());

    // Update timesUsed in MongoDB
    wordService.recordAnswer(request.getWordId(), correct);

    return correct ? "{\"result\":\"correct\"}" : "{\"result\":\"wrong\"}";
  }

  public static class AnswerRequest {
    private String wordId;
    private String selected;       // what the user picked
    private String correctAnswer;  // the correct answer

    public String getWordId() { return wordId; }
    public void setWordId(String wordId) { this.wordId = wordId; }
    public String getSelected() { return selected; }
    public void setSelected(String selected) { this.selected = selected; }
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
  }
}