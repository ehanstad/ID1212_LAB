package model;
import java.util.HashMap;
import java.util.Iterator;

public class Quiz implements java.io.Serializable {

  private HashMap<Integer, Question> questions = new HashMap<Integer, Question>();

  public Quiz() {

  }
  
  public void setAnswered(Integer id,String answered) {
      questions.get(id).setAnswered(answered);
  }
  
  public String getQuiz() {
      StringBuilder sb = new StringBuilder();
      Iterator<HashMap.Entry<Integer,Question>> iter = questions.entrySet().iterator();
      while(iter.hasNext()) {
        HashMap.Entry<Integer,Question> entry = iter.next();
        Question nElem = entry.getValue();
        sb.append(nElem.getQuestion());
        sb.append("\n");
      }
      return sb.toString();
  }
  
  public String getAlternatives() {
      StringBuilder sb = new StringBuilder();
      Iterator<HashMap.Entry<Integer,Question>> iter = questions.entrySet().iterator();
      while(iter.hasNext()) {
        HashMap.Entry<Integer,Question> entry = iter.next();
        Question nElem = entry.getValue();
        sb.append(nElem.getAlternatives());
      }
      return sb.toString();
  }

  public void addQuestion(Integer id, String question) {
    Question quest = new Question(id,question);
    questions.put(id,quest);
  }

  public void addAlternative(Integer qid, int aid, String alternative, boolean correct) {
      questions.get(qid).addAlternative(aid, alternative, correct);
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
