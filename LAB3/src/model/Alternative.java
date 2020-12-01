package model;

public class Alternative {

  private boolean answer;
  private boolean answered;

  public Alternative(boolean answer) {
    this.answer=answer;
  }

  public void setAnswered(boolean answered) {
    this.answered=answered;
  }

  public boolean checkCorrect() {
    return answer==answered;
  }

}
