package model;
import java.util.HashMap;
import java.util.Iterator;

public class Question {

  private String question;
  private HashMap<Integer, Alternative> alternatives = new HashMap<Integer, Alternative>();

  public Question(String question) {
    this.question = question;
  }
  
  public String getQuestion() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.question);
      Iterator<HashMap.Entry<Integer,Alternative>> iter = alternatives.entrySet().iterator();
      while(iter.hasNext()) {
        HashMap.Entry<Integer,Alternative> entry = iter.next();
        Alternative nElem = entry.getValue();
        sb.append(nElem.getAlternative());
        sb.append(",");
      }
      return sb.toString();
  }

  public void addAlternative(Integer id, String alternative, boolean answer) {
    Alternative alt = new Alternative(alternative,answer);
    alternatives.put(id,alt);
  }

  public void setAlternative(Integer id, boolean answered) {
    Alternative alt = alternatives.get(id);
    alt.setAnswered(answered);
  }

  public int getPoints() {
    Iterator<HashMap.Entry<Integer,Alternative>> iter = alternatives.entrySet().iterator();
    int points = 0;
    while(iter.hasNext()) {
      HashMap.Entry<Integer,Alternative> entry = iter.next();
      Alternative nElem = entry.getValue();
      if(nElem.checkCorrect())
        points++;
    }
    return points;
  }
}
