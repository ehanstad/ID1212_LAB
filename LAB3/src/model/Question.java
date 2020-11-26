package model;
import java.util.Hashtable;
import java.util.Enumeration;

public class Question {

  private String question;
  private Hashtable<Integer, Alternative> alternatives = new Hashtable<Integer, Alternative>();

  public Question(String question) {
    this.question = question;
  }

  public void addAlternative(Integer id, boolean answer) {
    Alternative alt = new Alternative(answer);
    alternatives.put(id,alt);
  }

  public void setAlternative(Integer id, boolean answered) {
    Alternative alt = alternatives.get(id);
    alt.setAnswered(answered);
  }

  public int getPoints() {
    Enumeration<Alternative> enumer = alternatives.values();
    int points = 0;
    while(enumer.hasMoreElements()) {
      Alternative nElem = enumer.nextElement();
      if(nElem.checkCorrect())
        points++;
    }
    return points;
  }
}
