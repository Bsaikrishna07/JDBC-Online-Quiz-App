package service;

import java.util.List;
import java.util.Scanner;

import dao.QuestionDao;
import dao.UserDao;
import model.Question;
import model.User;

public class QuizService {

    private Scanner scanner = new Scanner(System.in);
    private UserDao userDao = new UserDao();
    private QuestionDao questionDao = new QuestionDao();

    public void startQuizApp() {
        System.out.println("==== Welcome to the JDBC Quiz Application ====");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // consume newline

        User user = null;
        if (option == 1) {
            user = registerUser();
        } else if (option == 2) {
            user = loginUser();
        } else {
            System.out.println("Invalid option!");
            return;
        }

        if (user != null) {
            System.out.println("Welcome, " + user.getUsername() + "!");
            startQuiz();
        } else {
            System.out.println("Login/Register failed.");
        }
    }

    private User registerUser() {
        System.out.print("Enter username: ");
        String uname = scanner.nextLine();
        System.out.print("Enter password: ");
        String pwd = scanner.nextLine();

        User user = new User();
        user.setUsername(uname);
        user.setPassword(pwd);

        boolean success = userDao.registerUser(user);
        if (success) {
            System.out.println("✅ Registered successfully! Please login now.");
            return loginUser();
        } else {
            System.out.println("❌ Registration failed.");
            return null;
        }
    }

    private User loginUser() {
        System.out.print("Enter username: ");
        String uname = scanner.nextLine();
        System.out.print("Enter password: ");
        String pwd = scanner.nextLine();

        return userDao.loginUser(uname, pwd);
    }

    private void startQuiz() {
        List<Question> questions = questionDao.getAllQuestions();
        int score = 0;

        for (Question q : questions) {
            System.out.println("\n" + q.getQuestionText());
            System.out.println("A. " + q.getOptionA());
            System.out.println("B. " + q.getOptionB());
            System.out.println("C. " + q.getOptionC());
            System.out.println("D. " + q.getOptionD());
            System.out.print("Your answer (A/B/C/D): ");
            char answer = scanner.nextLine().toUpperCase().charAt(0);

            if (answer == q.getCorrectOption()) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Wrong! Correct Answer: " + q.getCorrectOption());
            }
        }

        System.out.println("\n🎉 Quiz Completed!");
        System.out.println("Your Score: " + score + " out of " + questions.size());
    }
}
