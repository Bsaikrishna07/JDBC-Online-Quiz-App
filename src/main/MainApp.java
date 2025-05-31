package main;

import service.QuizService;

public class MainApp {
    public static void main(String[] args) {
        QuizService quiz = new QuizService();
        quiz.startQuizApp();
    }
}
