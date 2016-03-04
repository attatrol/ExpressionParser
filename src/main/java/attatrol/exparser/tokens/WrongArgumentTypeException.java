package attatrol.exparser.tokens;

/**
 * Thrown when argument of illegal type is passed into operation
 * or function.
 *
 * @author atta_troll
 *
 */
public class WrongArgumentTypeException extends IllegalArgumentException {

	/**
	 * Serialization code.
	 */
	private static final long serialVersionUID = 3658027867021161915L;

	public WrongArgumentTypeException(Object arg, Token token) {
		super(String.format("Argument of wrong type \"%s\" is passed into %s",
				arg.getClass().getSimpleName(), token.toString()));
	}
}
