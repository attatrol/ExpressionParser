package attatrol.exparser;

import static attatrol.exparser.utility.InvalidLexerOutputStaticHolder.*;
import static attatrol.exparser.utility.LexerOutputStaticHolder.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import attatrol.exparser.BadExpressionException;
import attatrol.exparser.lexer.Lexeme;
import attatrol.exparser.lexer.Lexer;
import attatrol.exparser.lexer.LexerOutput;
import attatrol.exparser.tokens.Argument;

public class LexerTest
{

    /**
     * Basic test if the mock initializer is correct.
     */
    @Test
    public void initializerTest()
    {
        Assert.assertNotNull(INITIALIZER);
        Assert.assertNotNull(INITIALIZER.getFunctionNames());
        Assert.assertNotNull(INITIALIZER.getOperationNames());
    }
    
    /**
     * Marginal input test.
     */
    @Test
    public void testEmptyString()
    {
        
        try {
            lexerTestOnFormula("", EMPTY);
            lexerTestOnFormula(" ", EMPTY);
            lexerTestOnFormula("  ", EMPTY);
        }
        catch (BadExpressionException e) {
            e.printStackTrace();
            Assert.fail();
        }
        
    }
    
    /**
     * Test parentheses handling exclusively.
     * @return 
     */  
    @Test
    public void testInvalidPassingExpressions() {
        try {
            //Test parentheses handling exclusively.
            lexerTestOnFormula("()( )( (   )) ()", VALID_PARENTHESES);
            lexerTestOnFormula("(", SINGLE_LEFT_PARENTHESIS);
            lexerTestOnFormula(")", SINGLE_RIGHT_PARENTHESIS);
            lexerTestOnFormula("())(", WRONG_ORDER_PARENTHESES);
            //Test bad integrity cases
            lexerTestOnFormula("a b", BAD_INTEGRITY_1);
            lexerTestOnFormula("1 2", BAD_INTEGRITY_2);
            lexerTestOnFormula(")(", BAD_INTEGRITY_3);
            lexerTestOnFormula("4 (", BAD_INTEGRITY_4);
            //Test meaningless
            lexerTestOnFormula("max(,)+()+sqrt()", MEANINGLESS_1);
        }
        catch (BadExpressionException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
    
    /**
     * Major test for integrated in lexer operation validator.
     */
    @Test
    public void wrongPlacedOperationsTest() {
        testLexerOnFailingFormula("1%%1");
        testLexerOnFailingFormula("1+<<1");
        testLexerOnFailingFormula("1-%1");
        testLexerOnFailingFormula("<<1");
        testLexerOnFailingFormula("1+");
        testLexerOnFailingFormula(",<<,");
        testLexerOnFailingFormula(",+,");
        testLexerOnFailingFormula("(<<(");
        testLexerOnFailingFormula("),%)");
        testLexerOnFailingFormula(" 1 << 1 ");
        testLexerOnFailingFormula("a(+)b");
        
    }
        

    /**
     * Different formulas are used as tests for sequential processors used in lexer.
     * There are 6 stages of lexing, each of them highlights some type of lexeme from the raw text,
     * first 4 stages are instances of LexerProcessor class:</br>
     * 1. parentheses and commas processor</br>
     * 2. preliminary operation processor</br>
     * 3. constants processor</br>
     * 4. functions processor</br>
     * 5. highlighting of unbounded arguments and forming their list</br>
     * 6. defining of operation type</br>
     */  
    @Test
    public void testComplexExpressions()
    {
        try {
            //only argument in input
            lexerTestOnFormula("a", TRIVIAL_1);
            lexerTestOnFormula("1", TRIVIAL_2);
            //simple test 1+2
            lexerTestOnFormula("1+2", SIMPLE_1);
            //simple test (a+2)*b
            lexerTestOnFormula("(a+2)*b", SIMPLE_2);
            //simple test max(-a<<,-b<<)
            lexerTestOnFormula("max(-a<<,-b<<)", SIMPLE_3);
            //simple test zero argument function
            lexerTestOnFormula("pi()", SIMPLE_4);
            //some rare parenthesis compositions
            lexerTestOnFormula("max (((a+b)%(c+d)),(a+c))", SIMPLE_5);
            //Test for operations and constants processors (1,2,3,6)
            lexerTestOnFormula("-25.6+100.32-12/  7", SIMPLE_6);
            //Test for operations, functions, parentheses and constants processors (1,2,3,4,6)
            lexerTestOnFormula("max(122, 12.4) - +(1*3+sqrt(12+5)*-(4.6/3.1))", COMPLEX_1);
            //Test for operations, functions, parentheses and constants processors, also for argument handling (all stages)
            lexerTestOnFormula("anthony1*anthony2/max(anthony1-2, anthony2-2)<<-13.4+sqrt(d) - -c<<<< +6<<",
                    COMPLEX_2);
            //Overall lexer test, also some border cases of infix and posfix operations are in view (all stages)
            lexerTestOnFormula("---max(max(max(max(max(a,b), max(c, d)), 1),23b  <<<<  <<<<), --c+d/(a+b))<<<<",
                    COMPLEX_3);
            
        }
        catch (BadExpressionException e) {
            e.printStackTrace();
            Assert.fail("Incorrectly placed operation in input");
        }
    }

    private void lexerTestOnFormula(String formula, LexerOutput expectedOutput)
            throws BadExpressionException
    {
        LexerOutput output = Lexer.lexing(formula, INITIALIZER);
        assertLexerOutput(output, expectedOutput);
    }

    private static void assertLexerOutput(LexerOutput actual, LexerOutput expected)
    {
        final List<Argument> args0 = actual.getArguments();
        final List<Argument> args1 = expected.getArguments();
        final List<Lexeme> lexemes0 = actual.getLexemes();
        final List<Lexeme> lexemes1 = expected.getLexemes();

        if (args1 != null && args1 != null && args0.size() == args1.size()) {
            for (int i = 0; i < args0.size(); i++) {
                if (!args0.get(i).getDesignation()
                        .equals(args0.get(i).getDesignation())) {
                    Assert.fail("Inequality in "+ i + " member of arguments list");
                }
            }
        }
        else {
            Assert.fail("Arguments list inequality");
        }
        if (lexemes0 != null && lexemes1 != null
                && lexemes0.size() == lexemes1.size()) {
            for (int i = 0; i < lexemes0.size(); i++) {
                Assert.assertTrue("Inequality in "+ i + " member of lexemes list",
                        lexemeEquals(lexemes0.get(i),lexemes1.get(i)));
                if (!lexemes0.get(i).equals(lexemes1.get(i))) {
                    Assert.fail("Inequality in "+ i + " member of lexemes list");
                }
            }
        }
        else {
            Assert.fail("Lexemes list inequality");
        }
    }
    
    public static boolean lexemeEquals(Lexeme lexeme, Object object) {
        if (object instanceof Lexeme) {
            Lexeme other = (Lexeme) object;
        return lexeme.getStart() == other.getStart()
                && lexeme.getEnd() == other.getEnd()
                && lexeme.getToken().getClass().equals(other.getToken().getClass());
        }
        else {
            return false;
        }
    }
    
    private void testLexerOnFailingFormula(String formula)
    {
        try {
            Lexer.lexing(formula, INITIALIZER);
        }
        catch(BadExpressionException e) {
            return;
        }
        Assert.fail("Expected exception wasn't thrown");
    }
}
