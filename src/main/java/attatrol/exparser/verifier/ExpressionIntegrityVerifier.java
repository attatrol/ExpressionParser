package attatrol.exparser.verifier;

import java.util.List;

import attatrol.exparser.BadExpressionException;
import attatrol.exparser.lexer.Lexeme;
import attatrol.exparser.tokens.Argument;
import attatrol.exparser.tokens.Comma;
import attatrol.exparser.tokens.Constant;
import attatrol.exparser.tokens.InfixOperation;
import attatrol.exparser.tokens.LeftParenthesis;
import attatrol.exparser.tokens.PostfixOperation;
import attatrol.exparser.tokens.PrefixOperation;
import attatrol.exparser.tokens.RightParenthesis;
import attatrol.exparser.tokens.Token;

/**
 * Checks for overall integrity of the expression:</br>
 * 1. arguments and constants should be delimitered by parentheses, commas and operations;</br>
 * 2. right and left parentheses should never neighbour i.e. )(.
 * @author atta_troll
 *
 */
public class ExpressionIntegrityVerifier
{
    /**
     * Verifies constants and arguments for their valid placement.
     * 
     * @param lexemes the incoming lexeme list.
     * @throws BadExpressionException thrown on invalid verification.
     */
    public static void verify(List<Lexeme> lexemes) 
        throws BadExpressionException {
        for (int i=0; i<lexemes.size(); i++) {
            final Token archetype = lexemes.get(i).getToken();
            if (archetype instanceof Constant 
                    || archetype instanceof Argument) {
                final Token previous = i == 0 ? null : lexemes.get(i - 1).getToken();
                final Token next = i == lexemes.size() - 1 ? null 
                        : lexemes.get(i + 1).getToken();
                if (!validArgumentLeftNeighbour(previous)
                        || !validArgumentRightNeighbour(next)
                        && !(previous instanceof LeftParenthesis
                                && next instanceof RightParenthesis)) {
                    throw new BadExpressionException
                        (lexemes.get(i), "Attribute or constant has bad neighbour");
                }
                
            }
            else if (archetype instanceof RightParenthesis 
                    && i != lexemes.size() - 1
                    && lexemes.get(i + 1).getToken() instanceof LeftParenthesis) {
                throw new BadExpressionException
                (lexemes.get(i), "Right parenthesis has left one next to it.");
            }
        }
        
    }

    private static boolean validArgumentLeftNeighbour(Token previous)
    {
        return previous instanceof InfixOperation
                || previous instanceof LeftParenthesis
                || previous instanceof PrefixOperation
                || previous instanceof Comma 
                || previous==null;
    }
    
    private static boolean validArgumentRightNeighbour(Token next)
    {
        return next instanceof InfixOperation
                || next instanceof RightParenthesis
                || next instanceof PostfixOperation
                || next instanceof Comma 
                || next==null;
    }

}
