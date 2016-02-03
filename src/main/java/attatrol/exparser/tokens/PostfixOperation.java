package attatrol.exparser.tokens;

/**
 * Describes any postfix operation, e.g. postfix increment x++.
 * @author atta_troll
 *
 */
public abstract class PostfixOperation extends Operation {

  public PostfixOperation() {
  }

  /**
   * Calculates value of the function.
   * @param arg the operand
   * @return calculated value
   */
  public abstract double calculate(double arg);
}
