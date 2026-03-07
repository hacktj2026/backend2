package com.example.hacktj;

import com.example.hacktj.model.Word;
import com.example.hacktj.problemBuilder;
import com.example.hacktj.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*") // allows frontend to call this
public class GameController {

    @Autowired
    private WordService wordService;

    // Frontend calls this to get a new problem
    @GetMapping("/next")
    public String nextProblem() throws Exception {
        Word word = wordService.getNext();
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
}