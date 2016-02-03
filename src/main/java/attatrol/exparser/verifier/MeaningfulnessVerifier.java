package attatrol.exparser.verifier;

import java.util.List;

import attatrol.exparser.BadExpressionException;
import attatrol.exparser.lexer.Lexeme;
import attatrol.exparser.tokens.Argument;
import attatrol.exparser.tokens.Constant;
import attatrol.exparser.tokens.Function;
import attatrol.exparser.tokens.Token;

/**
 * Expression should have at least single constant or argument
 * or zero arity function to be meaningful.
 *
 * @author atta_troll
 *
 */
public class MeaningfulnessVerifier
{
    /**
     * Checks if expression has at least single constant or argument
     * or zero arity function.
     * @param lexemes the incoming list of lexemes
     * @throws BadExpressionException if formula isn't meaningful.
     */
    public static void verify(List<Lexeme> lexemes) 
            throws BadExpressionException
    {
            boolean meaningful = false;
            for (Lexeme lexeme:lexemes) {
                final Token archetype = lexeme.getToken();
                if (archetype instanceof Argument 
                        || archetype instanceof Constant
                        || (archetype instanceof Function 
                                && ((Function) archetype).getArity() == 0)) {
                    meaningful = true;
                }    
            }
           if (!meaningful) {
               throw new BadExpressionException("Argument-less formulas are meaningless."); 
           }
    }

}
