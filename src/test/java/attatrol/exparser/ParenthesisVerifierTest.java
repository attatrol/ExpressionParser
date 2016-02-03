package attatrol.exparser;

import static attatrol.exparser.utility.InvalidLexerOutputStaticHolder.*;
import static attatrol.exparser.utility.LexerOutputStaticHolder.*;

import org.junit.Assert;
import org.junit.Test;

import attatrol.exparser.BadExpressionException;
import attatrol.exparser.lexer.LexerOutput;
import attatrol.exparser.verifier.ParenthesisLevelVerifier;

public class ParenthesisVerifierTest
{
    @Test
    public void testValidExpressions()
    {
        testValidatorOnValidLexemes(SIMPLE_1);
        testValidatorOnValidLexemes(SIMPLE_2);
        testValidatorOnValidLexemes(SIMPLE_3);
        testValidatorOnValidLexemes(SIMPLE_4);
        testValidatorOnValidLexemes(SIMPLE_5);
        testValidatorOnValidLexemes(SIMPLE_6);
        testValidatorOnValidLexemes(COMPLEX_1);
        testValidatorOnValidLexemes(COMPLEX_2);
        testValidatorOnValidLexemes(COMPLEX_3);
        testValidatorOnValidLexemes(TRIVIAL_1);
        testValidatorOnValidLexemes(TRIVIAL_2);
        //here it is valid
        testValidatorOnValidLexemes(VALID_PARENTHESES);
    }
    
    @Test
    public void testInvalidRelevantExpressions()
    {
        testValidatorOnInvalidLexemes(SINGLE_LEFT_PARENTHESIS);
        testValidatorOnInvalidLexemes(SINGLE_RIGHT_PARENTHESIS);
        testValidatorOnInvalidLexemes(WRONG_ORDER_PARENTHESES);
    }
    
    private void testValidatorOnValidLexemes(LexerOutput input) 
    {
        try {
            ParenthesisLevelVerifier.verify(input.getLexemes());
        }
        catch(BadExpressionException e) {
            Assert.fail("Expected exception wasn't thrown");
        }
    }
        

    private void testValidatorOnInvalidLexemes(LexerOutput input)
    {
        try {
            ParenthesisLevelVerifier.verify(input.getLexemes());
        }
        catch(BadExpressionException e) {
            return;
        }
        Assert.fail("Expected exception wasn't thrown");
    }

}
