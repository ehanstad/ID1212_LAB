package model;

public class Alternative {

  private String alternative;
  private boolean answer;
  private boolean answered;

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
    return answer==answered;
  }

}
