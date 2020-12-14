package com.id1212.lab3maven.model;

public class Alternative {

  private String alternative;
  private boolean answer;
  private boolean answered=false;

  public Alternative(String alternative, boolean answer) {
    this.alternative=alternative;
    this.answer=answer;
  }

  public String getAlternative() {
      return this.alternative;
  }
  
  public void setAnswered(boolean answered) {
    this.answered=answered;
  }

  public boolean checkCorrect() {
    return this.answered==this.answer;
  }

}
