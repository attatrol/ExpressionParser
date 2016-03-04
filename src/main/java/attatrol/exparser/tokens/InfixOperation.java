package attatrol.exparser.tokens;

/**
 * Describes any infix operation, e.g. common sum: 2 + 5.
 * 
 * @author atta_troll
 *
 */
public abstract class InfixOperation extends Operation {

    /**
     * Priority must not exceed 1000.
     */
    private int priority;

    public InfixOperation(int priority) {
        this.priority = priority;
    }
    
    public int getPriority() {
        return priority;
    }

  /**
   * Calculates value of the function.
   * @param arg1 first operand
   * @param arg2 second operand
   * @return calculated value
   * @throws WrongArgumentTypeException if wrong type of argument is passed into function. 
   */
  public abstract Object calculate(Object arg1, Object arg2) throws WrongArgumentTypeException;
}
