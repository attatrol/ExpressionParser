package attatrol.exparser;

import static attatrol.exparser.utility.InvalidLexerOutputStaticHolder.*;
import static attatrol.exparser.utility.LexerOutputStaticHolder.*;

import org.junit.Assert;
import org.junit.Test;

import attatrol.exparser.BadExpressionException;
import attatrol.exparser.lexer.LexerOutput;
import attatrol.exparser.verifier.FunctionSyntaxVerifier;

public class FunctionSyntaxVerifierTest
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
    }
    
    @Test
    public void testInvalidRelevantExpressions()
    {
        testValidatorOnInvalidLexemes(BAD_FUNCTION_1);
        testValidatorOnInvalidLexemes(BAD_FUNCTION_2);
        testValidatorOnInvalidLexemes(BAD_FUNCTION_3);
        testValidatorOnInvalidLexemes(BAD_FUNCTION_4);
        testValidatorOnInvalidLexemes(BAD_FUNCTION_5);
        testValidatorOnInvalidLexemes(BAD_FUNCTION_6);
        testValidatorOnInvalidLexemes(BAD_FUNCTION_7);
    }
    
    private void testValidatorOnValidLexemes(LexerOutput input) 
    {
        try {
            FunctionSyntaxVerifier.verify(input.getLexemes());
        }
        catch(BadExpressionException e) {
            Assert.fail("Formula syntax violation");
        }
    }
        

    private void testValidatorOnInvalidLexemes(LexerOutput input)
    {
        try {
            FunctionSyntaxVerifier.verify(input.getLexemes());
        }
        catch(BadExpressionException e) {
            return;
        }
        Assert.fail("Expected exception wasn't thrown");
    }
}
