package attatrol.exparser.verifier;

import java.util.Iterator;
import java.util.List;

import attatrol.exparser.BadExpressionException;
import attatrol.exparser.lexer.Lexeme;
import attatrol.exparser.tokens.Argument;
import attatrol.exparser.tokens.Comma;
import attatrol.exparser.tokens.Constant;
import attatrol.exparser.tokens.Function;
import attatrol.exparser.tokens.LeftParenthesis;
import attatrol.exparser.tokens.PrefixOperation;
import attatrol.exparser.tokens.RightParenthesis;
import attatrol.exparser.tokens.Token;

/**
 * Performs formula validation (basic syntax and arity checks), also checks for valid comma
 * positions.
 * @author atta_troll
 */
public class FunctionSyntaxVerifier
{
    /**
     * Performs formula validation (basic syntax and arity checks), also checks for valid comma
     * positions.
     * @param lexemes
     *        the incoming list of lexemes.
     * @throws BadExpressionException
     *         thrown on syntax errors.
     */
    public static void verify(List<Lexeme> lexemes)
            throws BadExpressionException
    {
        Iterator<Lexeme> it = lexemes.listIterator();
        while (it.hasNext()) {
            Lexeme lexeme = it.next();
            if (lexeme.getToken() instanceof Function) {
                it = functionHandling(lexeme, it);
            }
            else if (lexeme.getToken() instanceof Comma) {
                throw new BadExpressionException(lexeme,
                        "No commas out of the function's cortege allowed in formula.");
            }

        }
    }

    private static Iterator<Lexeme> functionHandling(Lexeme lexeme, Iterator<Lexeme> it)
            throws BadExpressionException
    {
        final int constArity = ((Function) lexeme.getToken()).getArity();
        int arity = constArity == 0 ? 0 : constArity - 1;
        if (it.hasNext()) {
            Token leftParenthesis = it.next().getToken();
            if (leftParenthesis instanceof LeftParenthesis) {
                int parenthesisLevel = 1;
                boolean meaningfulDelimiterNeeded = constArity != 0;
                while (parenthesisLevel != 0 && it.hasNext()) {
                    Token type = it.next().getToken();
                    if (meaningfulDelimiterNeeded) {
                        meaningfulDelimiterNeeded = false;
                        if (!isMeaningfulDelimiter(type)) {
                            throw new BadExpressionException(lexeme,
                                    "Function syntax error");
                        }
                    }
                    if (type instanceof Comma) {
                        arity--;
                        meaningfulDelimiterNeeded = true;
                    }
                    else if (type instanceof LeftParenthesis) {
                        parenthesisLevel++;
                    }
                    else if (type instanceof RightParenthesis) {
                        parenthesisLevel--;
                    }
                    else if (type instanceof Function) {
                        it = functionHandling(lexeme, it);
                    }
                }
                if (arity != 0) {
                    throw new BadExpressionException(lexeme,
                            "Function incorrect arity, should be " + constArity);
                }
                if (parenthesisLevel != 0) {
                    throw new BadExpressionException(lexeme,
                            "Function has no closing parenthesis");
                }
            }
            else {
                throw new BadExpressionException(lexeme, "Left parenthesis absent.");
            }
        }
        return it;
        
    }

    private static boolean isMeaningfulDelimiter(Token type)
    {
        return type instanceof Argument || type instanceof Constant
                || type instanceof PrefixOperation
                || type instanceof LeftParenthesis
                || type instanceof Function;
    }

}
