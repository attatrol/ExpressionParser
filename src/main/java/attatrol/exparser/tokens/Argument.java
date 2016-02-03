package attatrol.exparser.tokens;

/**
 * Describes some kind of argument, unknown symbol encountered by parser.
 * @author atta_troll
 *
 */
public final class Argument implements Token {

    /**
     * Symbolic string that defines the atomic unit of parsing.
     */
    private String designation;

    public Argument(String designation) {
      this.designation = designation;
    }
    
    public String getDesignation() {
      return designation;
    }

}
