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
   * @throws WrongArgumentTypeException if wrong type of argument is passed into function. 
   */
  public abstract Object calculate(Object arg) throws WrongArgumentTypeException;
}
