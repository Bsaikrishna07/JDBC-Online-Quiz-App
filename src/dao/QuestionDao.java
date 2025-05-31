package dao;

import java.sql.*;
import java.util.*;
import model.Question;

public class QuestionDao {

    public boolean addQuestion(Question q) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, q.getQuestionText());
            ps.setString(2, q.getOptionA());
            ps.setString(3, q.getOptionB());
            ps.setString(4, q.getOptionC());
            ps.setString(5, q.getOptionD());
            ps.setString(6, String.valueOf(q.getCorrectOption()));
            int i = ps.executeUpdate();
            conn.close();
            return i > 0;
        } catch (Exception e) {
            System.out.println("Error in addQuestion: " + e);
            return false;
        }
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions");

            while (rs.next()) {
                Question q = new Question();
                q.setId(rs.getInt("id"));
                q.setQuestionText(rs.getString("question_text"));
                q.setOptionA(rs.getString("option_a"));
                q.setOptionB(rs.getString("option_b"));
                q.setOptionC(rs.getString("option_c"));
                q.setOptionD(rs.getString("option_d"));
                q.setCorrectOption(rs.getString("correct_option").charAt(0));
                questions.add(q);
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Error in getAllQuestions: " + e);
        }
        return questions;
    }
}
