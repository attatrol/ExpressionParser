package attatrol.exparser;

import static attatrol.exparser.utility.InvalidLexerOutputStaticHolder.*;
import static attatrol.exparser.utility.LexerOutputStaticHolder.*;

import org.junit.Assert;
import org.junit.Test;

import attatrol.exparser.BadExpressionException;
import attatrol.exparser.lexer.LexerOutput;
import attatrol.exparser.verifier.ExpressionIntegrityVerifier;

public class ExpressionIntegrityVerifierTest
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
        testValidatorOnInvalidLexemes(BAD_INTEGRITY_1);
        testValidatorOnInvalidLexemes(BAD_INTEGRITY_2);
        testValidatorOnInvalidLexemes(BAD_INTEGRITY_3);
        testValidatorOnInvalidLexemes(BAD_INTEGRITY_4);
    }
    
    private void testValidatorOnValidLexemes(LexerOutput input) 
    {
        try {
            ExpressionIntegrityVerifier.verify(input.getLexemes());
        }
        catch(BadExpressionException e) {
            Assert.fail("Expression integrity violation");
        }
    }
        

    private void testValidatorOnInvalidLexemes(LexerOutput input)
    {
        try {
            ExpressionIntegrityVerifier.verify(input.getLexemes());
        }
        catch(BadExpressionException e) {
            return;
        }
        Assert.fail("Expected exception wasn't thrown");
    }

}
