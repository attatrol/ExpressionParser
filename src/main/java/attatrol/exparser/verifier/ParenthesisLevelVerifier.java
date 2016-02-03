package attatrol.exparser.verifier;

import java.util.List;

import attatrol.exparser.BadExpressionException;
import attatrol.exparser.lexer.Lexeme;
import attatrol.exparser.tokens.LeftParenthesis;
import attatrol.exparser.tokens.RightParenthesis;
import attatrol.exparser.tokens.Token;

/**
 * Checks basic parenthesis leveling.
 * @author atta_troll
 *
 */
public class ParenthesisLevelVerifier 
{
    /**
     *Validates formula's parenthesis levelling.
     * 
     * @param lexemes the incoming lexemes list.
     * @throws BadExpressionException thrown on invalid parenthesis level.
     */
    public static void verify(List<Lexeme> lexemes) 
            throws BadExpressionException
    {
        int level = 0;
        for (Lexeme lexeme:lexemes) {
            final Token archetype = lexeme.getToken();
            if (archetype instanceof LeftParenthesis) {
                level++;
            }
            if (archetype instanceof RightParenthesis) {
                level--;
                if (level<0) {
                    throw new BadExpressionException(lexeme, "Negative parenthesis level");
                }
            }
        }
        if (level != 0) {
            throw new BadExpressionException
            ("Level value" + level + "in the end of the formula, expected 0");
        } 
    }
}
