package model;
import java.util.HashMap;
import java.util.Iterator;

public class Quiz {

  private HashMap<Integer, Question> questions = new HashMap<Integer, Question>();

  public Quiz() {

  }

  public void addQuestion(Integer id, String question) {
    Question quest = new Question(question);
    questions.put(id,quest);
  }

  public int countPoints() {
    Iterator<HashMap.Entry<Integer,Question>> iter = questions.entrySet().iterator();
    int points = 0;
    while(iter.hasNext()) {
      HashMap.Entry<Integer,Question> entry = iter.next();
      Question nElem = entry.getValue();
      points+=nElem.getPoints();
    }
    return points;
  }
}
