package attatrol.exparser.lexer;

/**
 * POJO, basic class for all atomic units in the lexer's processing sequence.
 * 
 * @author atta_troll
 *
 */
public abstract class LexerDataUnit {

  private int start;
  private int end;

  public LexerDataUnit(int start, int end) {
    super();
    this.start = start;
    this.end = end;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

}
