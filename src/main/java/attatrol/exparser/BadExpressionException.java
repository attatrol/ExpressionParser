package attatrol.exparser;

import attatrol.exparser.lexer.LexerDataUnit;

/**
 * Thrown on failure to parse input formula.
 * May contain exact coordinates of the erroneous symbols.
 * 
 * @author atta_troll
 *
 */
public class BadExpressionException extends Exception {
  
  /**
   * 
   */
  private static final long serialVersionUID = -7576294909798462702L;
  
  private int startCoord;
  private int endCoord;
   
  public BadExpressionException(int startCoord, int endCoord, String message) {
    super(message);
    this.startCoord = startCoord;
    this.endCoord = endCoord;
  }
  
  public BadExpressionException(LexerDataUnit unit, String message) {
      super(message);
      this.startCoord = unit.getStart();
      this.endCoord = unit.getEnd();
  }
  
  public BadExpressionException(String message) {
    super(message);
    this.startCoord = -1;
    this.endCoord = -1;
  }

  public int getStartCoord() {
    return startCoord;
  }
  public int getEndCoord() {
    return endCoord;
  }
  

}
