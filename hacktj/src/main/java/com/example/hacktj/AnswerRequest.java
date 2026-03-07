package com.example.hacktj;

public class AnswerRequest {
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