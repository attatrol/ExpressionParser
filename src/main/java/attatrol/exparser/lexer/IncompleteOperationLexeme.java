package attatrol.exparser.lexer;

/**
 * Used by the lexer exclusively between lexing stages 2 and 6.
 * 
 * @author atta_troll
 *
 */
class IncompleteOperationLexeme extends LexerDataUnit {

  private String data;
  
  public IncompleteOperationLexeme(int start, int end, String data) {
    super(start, end);
    this.data = data;
  }

  public String getData() {
    return data;
  }
  

}
