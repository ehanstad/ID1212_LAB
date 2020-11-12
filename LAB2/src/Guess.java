import java.lang.Math;
import java.net.*;

public class Guess {

  private int noGuesses = 0;
  private int magicNumber;
  private int guessedNumber;

  public Guess() {
    this.magicNumber = (int)(Math.random()*100)+1;
  }

  public void setGuessedNumber(int gn) {
    this.guessedNumber = gn;
    this.noGuesses++;
  }

  public int getGuessedNumber() {
    return this.guessedNumber;
  }

  public int getNoGuesses() {
    return this.noGuesses;
  }

  public int getMagicNumber() {
    return this.magicNumber;
  }

  public short checkGuess(int guess) {
    if(guess < this.magicNumber){
      return -1;
    } else if(guess > this.magicNumber) {
      return 1;
    } else {
      return 0;
    }
  }
}
