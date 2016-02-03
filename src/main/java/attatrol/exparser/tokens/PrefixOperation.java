package attatrol.exparser.tokens;

/**
 * Describes any prefix operation, e.g. unary minus: -1.
 * 
 * @author atta_troll
 *
 */
public abstract class PrefixOperation extends Operation {

   public PrefixOperation() {
  }

   /**
    * Calculates value of the function.
    * @param arg the operand
    * @return calculated value
    */
  public abstract double calculate(double arg);
}
