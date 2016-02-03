package attatrol.exparser.lexer;

/**
 * POJO, contains unprocessed parts of the formula during lexing.
 * 
 * @author atta_troll
 *
 */
class UnprocessedDataUnit extends LexerDataUnit{

  private String data;
  
  public UnprocessedDataUnit(int start, int end, String data) {
    super(start, end);
    this.data = data;
  }

  public String getData() {
    return data;
  }
  
}
