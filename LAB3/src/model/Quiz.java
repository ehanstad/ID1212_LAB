package model;
import java.util.Hashtable;
import java.util.Enumeration;

public class Quiz {

  private Hashtable<Integer, Question> questions = new Hashtable<Integer, Question>();

  public Quiz() {

  }

  public void addQuestion(Integer id, String question) {
    Question quest = new Question(question);
    questions.add(id,quest);
  }

  public int countPoints() {
    Enumeration<Question> enumer = alternatives.values();
    int points = 0;
    while(enumer.hasMoreElements()) {
      Question nElem = enumer.nextElement();
      points+=nElem.getPoints();
    }
    return points;
  }
}
