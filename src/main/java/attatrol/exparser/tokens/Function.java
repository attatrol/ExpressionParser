package attatrol.exparser.tokens;

public abstract class Function implements Token{
  

  
  /**
   * Arity of the function.
   */
  private int arity;


  public Function(int arity) {
    this.arity = arity;
  }

  public int getArity() {
    return arity;
  }
  
  /**
   * Calculates function value with given arguments.
   * @param cortege the arguments.
   * @return the function value.
   * @throws IllegalArgumentException thrown if arity isn't equal to the number of arguments.
   */
  public double calculate(double...cortege) throws IllegalArgumentException {
    if (cortege.length == this.arity) {
      return innerCalculate(cortege);
    }
    else {
      throw new IllegalArgumentException("Wrong number of arguments encountered in function.");
    }
  }

  /**
   * Contains processing of the calculation.
   * @param args the arguments.
   * @return  the function value.
   */
  protected abstract double innerCalculate(double[] args);

}
