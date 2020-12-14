package com.id1212.lab3maven.model;
import java.util.HashMap;
import java.util.Iterator;

public class Question {

  private String question;
  private Integer qid;
  private HashMap<Integer, Alternative> alternatives = new HashMap<Integer, Alternative>();

  public Question(Integer qid,String question) {
    this.qid=qid;
    this.question = question;
  }
  
  public String getQuestion() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.question + "#");
      Iterator<HashMap.Entry<Integer,Alternative>> iter = alternatives.entrySet().iterator();
      while(iter.hasNext()) {
        HashMap.Entry<Integer,Alternative> entry = iter.next();
        Alternative nElem = entry.getValue();
        sb.append(nElem.getAlternative());
        sb.append("#");
      }
      return sb.toString();
  }
  
  public String getAlternatives() {
      StringBuilder sb = new StringBuilder();
      Iterator<HashMap.Entry<Integer,Alternative>> iter = alternatives.entrySet().iterator();
      while(iter.hasNext()) {
        HashMap.Entry<Integer,Alternative> entry = iter.next();
        Alternative nElem = entry.getValue();
        sb.append(nElem.getAlternative()+"#"+this.qid+"#");
      }
      return sb.toString();
  }
  
  public void setAnswered(String answered){
      Iterator<HashMap.Entry<Integer,Alternative>> iter = alternatives.entrySet().iterator();
      while(iter.hasNext()) {
        HashMap.Entry<Integer,Alternative> entry = iter.next();
        Alternative nElem = entry.getValue();
        if(nElem.getAlternative().equals(answered)) {
            nElem.setAnswered(true);
        }
      }
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
