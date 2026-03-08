package com.example.hacktj;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hacktj.model.Conjugation;
import com.example.hacktj.model.ConjugationBuilder;
import com.example.hacktj.model.User;
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
  public ProblemResponse getProblem(@RequestParam(value = "username") String username) throws Exception {
    if(userRepository.findByName(username) == null)
        userRepository.save(new User(username));
    User user = userRepository.findByName(username);

    Word word = wordService.getNext();
    if (word == null) {
      throw new Exception("No word found");
    }
    ObjectMapper mapper = new ObjectMapper();
    System.out.println("Word type: " + word.getWordType());
    if(word.getWordType().equals("verb") && Math.random() < 0.5) {
      ConjugationBuilder builder = new ConjugationBuilder(word);
      String problemJson = builder.problem(user);
      
      var problemData = mapper.readTree(problemJson);
      
      String[] choices = mapper.convertValue(problemData.get("choices"), String[].class);
      
      return new ProblemResponse(
        word.getId(),
        problemData.get("question").asText(),
        problemData.get("correctAnswer").asText(),
        choices,
        word.getSkillLevel(),
        problemData.get("ConjugationType").asText(),
        problemData.get("Pronoun").asText()
      );
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

  @PostMapping("/check-answer")
  public AnswerResponse checkAnswer(@RequestBody AnswerRequest request) {
    if(userRepository.findByName(request.getUsername()) == null)
      userRepository.save(new User(request.getUsername()));
    Word word = wordRepository.findByWord(request.getWordName());
    boolean correct;
    if(request.getConjugationType() != null)
    {
      String correctAnswer;
      switch(request.getConjugationType())
      {
        case "present":
         correctAnswer = Conjugation.conjugatePresent(word, request.getPronoun());
          correct = correctAnswer.equals(request.getSelected());
          wordService.rightOrWrong(correct, word.getLevel(), userRepository.findByName(request.getUsername()));
          userRepository.findByName(request.getUsername()).updateSkill(word, correct);
          return new AnswerResponse(correct);
        case "future":
          correctAnswer = Conjugation.conjugateFuture(word, request.getPronoun());
          correct = correctAnswer.equals(request.getSelected());
          wordService.rightOrWrong(correct, word.getLevel(), userRepository.findByName(request.getUsername()));
          userRepository.findByName(request.getUsername()).updateSkill(word, correct);
          return new AnswerResponse(correct);
        case "conditional":
          correctAnswer = Conjugation.conjugateConditional(word, request.getPronoun());
          correct = correctAnswer.equals(request.getSelected());
          wordService.rightOrWrong(correct, word.getLevel(), userRepository.findByName(request.getUsername()));
          userRepository.findByName(request.getUsername()).updateSkill(word, correct);
          return new AnswerResponse(correct);
      }
    }
    correct = word.getMeaning().equals(request.getSelected());
    wordService.rightOrWrong(correct, word.getLevel(), userRepository.findByName(request.getUsername()));
    userRepository.findByName(request.getUsername()).updateSkill(word, correct);
    return new AnswerResponse(correct);
  }
  @DeleteMapping("/users")
  public void deleteAllUsers() {
      userRepository.deleteAll();
  }
  @GetMapping("/users")
  public List<User> getAllUsers() {
      return userRepository.findAll();
  }

  public static class ProblemResponse {
    public String wordId;
    public String question;
    public String correctAnswer;
    public String[] choices;
    public String level;
    public String conjugationType;
    public String pronoun;
    public ProblemResponse() {}
    
    public ProblemResponse(String wordId, String question, String correctAnswer, String[] choices, String level) {
      this.wordId = wordId;
      this.question = question;
      this.correctAnswer = correctAnswer;
      this.choices = choices;
      this.level = level;
      
    }

    public ProblemResponse(String wordId, String question, String correctAnswer, String[] choices, String level, String ConjugationLvl, String Pronoun) {
      this.wordId = wordId;
      this.question = question;
      this.correctAnswer = correctAnswer;
      this.choices = choices;
      this.level = level;
      this.conjugationType = ConjugationLvl;
      this.pronoun = Pronoun;
    }
  }

  public static class AnswerResponse {
    public boolean correct;
    public AnswerResponse(boolean correct) {
      this.correct = correct;
    }
  }

  public static class AnswerRequest {
    private String wordName;
    private String selected;
    private String username;
    private String conjugationType;
    private String Pronoun;
    public String getPronoun() { return Pronoun; }
    public String getWordName() { return wordName; }
    public String getSelected() { return selected; }
    public String getUsername() { return username; }
    public String getConjugationType() { return conjugationType; }
  }

  @GetMapping("/mcq/problem")
  public ProblemResponse getProblemMCQ(@RequestParam(value = "username") String username) throws Exception {
     User user = userRepository.findByName(username);

    Word word = wordService.getNext();
    if (word == null) {
      throw new Exception("No word found");
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

    @PostMapping("/mcq/check-answer")
  public AnswerResponse checkAnswerMCQ(@RequestBody AnswerRequest request) {
    if(userRepository.findByName(request.getUsername()) == null)
      userRepository.save(new User(request.getUsername()));
    Word word = wordRepository.findByWord(request.getWordName());
    boolean correct;
    
    correct = word.getMeaning().equals(request.getSelected());
    wordService.rightOrWrong(correct, word.getLevel(), userRepository.findByName(request.getUsername()));
    userRepository.findByName(request.getUsername()).updateSkill(word, correct);
    return new AnswerResponse(correct);
  }

  @PostMapping("/frq/check-answer")
  public AnswerResponse checkAnswerFRQ(@RequestBody AnswerRequest request) {
    if(userRepository.findByName(request.getUsername()) == null)
      userRepository.save(new User(request.getUsername()));
    Word word = wordRepository.findByWord(request.getWordName());
    boolean correct = false;
      String correctAnswer;
      switch(request.getConjugationType())
      {
        case "present":
         correctAnswer = Conjugation.conjugatePresent(word, request.getPronoun());
          correct = correctAnswer.equals(request.getSelected());
          wordService.rightOrWrong(correct, word.getLevel(), userRepository.findByName(request.getUsername()));
          userRepository.findByName(request.getUsername()).updateSkill(word, correct);
          return new AnswerResponse(correct);
        case "future":
          correctAnswer = Conjugation.conjugateFuture(word, request.getPronoun());
          correct = correctAnswer.equals(request.getSelected());
          wordService.rightOrWrong(correct, word.getLevel(), userRepository.findByName(request.getUsername()));
          userRepository.findByName(request.getUsername()).updateSkill(word, correct);
          return new AnswerResponse(correct);
        case "conditional":
          correctAnswer = Conjugation.conjugateConditional(word, request.getPronoun());
          correct = correctAnswer.equals(request.getSelected());
          wordService.rightOrWrong(correct, word.getLevel(), userRepository.findByName(request.getUsername()));
          userRepository.findByName(request.getUsername()).updateSkill(word, correct);
          return new AnswerResponse(correct);
      }

      return new AnswerResponse(correct);
    
  }

  @GetMapping("/frq/problem")
  public ProblemResponse getProblemFRQ(@RequestParam(value = "username") String username) throws Exception {
    if(userRepository.findByName(username) == null)
        userRepository.save(new User(username));
    User user = userRepository.findByName(username);

    Word word = wordService.getNext();
    if (word == null) {
      word = new Word("tener", "verb", "1", "to have", 1);
    }

    while(!word.getWordType().equals("verb")) {
      word = wordService.getNext();
    }
    ObjectMapper mapper = new ObjectMapper();
    
    
      ConjugationBuilder builder = new ConjugationBuilder(word);
      String problemJson = builder.problem(user);
      
      var problemData = mapper.readTree(problemJson);
      
      String[] choices = mapper.convertValue(problemData.get("choices"), String[].class);
      
      return new ProblemResponse(
      word.getId(),
      problemData.get("question").asText(),
      problemData.get("correctAnswer").asText(),
      new String[0],
      word.getSkillLevel() != null ? word.getSkillLevel() : "A1",
      problemData.get("conjugationType").asText(),  // lowercase c
      problemData.get("Pronoun").asText()            // capital P
      );
    
    }
  @GetMapping("/test")
    public String test() {
        wordService.test();
        return "done";
    }
}