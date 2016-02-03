package attatrol.exparser.lexer;

import attatrol.exparser.tokens.Token;

/**
 * POJO, union of a defined token and its position is the input string.
 * @author atta_troll
 *
 */
public class Lexeme extends LexerDataUnit {
  
  private Token token;
  
  public Lexeme(int start, int end, Token token) {
    super(start, end);
    this.token = token;
  }

  public Token getToken() {
    return token;
  }
  
  @Override
  public boolean equals(Object object) {
      if (object instanceof Lexeme) {
          Lexeme other = (Lexeme) object;
      return this.getStart() == other.getStart()
              && this.getEnd() == other.getEnd()
              && this.token.getClass().equals(other.token.getClass());
              //&& this.unit.getDesignation().equals(other.unit.getDesignation());
      }
      else {
          return false;
      }
  }
  
}
