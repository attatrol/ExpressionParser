package attatrol.exparser;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import attatrol.exparser.lexer.LexerOutput;
import attatrol.exparser.parser.Action;
import attatrol.exparser.parser.Expression;
import attatrol.exparser.parser.ParserProcessor;

import static attatrol.exparser.utility.ExpressionStaticHolder.*;
import static attatrol.exparser.utility.LexerOutputStaticHolder.*;
import static org.junit.Assert.*;

public class ParserProcessorTest
{

    @Test
    public void testValidLexerOutputs()
    {
        testParserProcessor(SIMPLE_1, EXPR_SIMPLE_1);
        testParserProcessor(SIMPLE_2, EXPR_SIMPLE_2);
        testParserProcessor(SIMPLE_3, EXPR_SIMPLE_3);
        testParserProcessor(SIMPLE_4, EXPR_SIMPLE_4);
        testParserProcessor(SIMPLE_5, EXPR_SIMPLE_5);
        testParserProcessor(SIMPLE_6, EXPR_SIMPLE_6);
        testParserProcessor(COMPLEX_1, EXPR_COMPLEX_1);
        testParserProcessor(COMPLEX_2, EXPR_COMPLEX_2);
        testParserProcessor(COMPLEX_3, EXPR_COMPLEX_3);
        testParserProcessor(TRIVIAL_1, EXPR_TRIVIAL_1);
        testParserProcessor(TRIVIAL_2, EXPR_TRIVIAL_2);
        
    }
    
    private static void testParserProcessor(LexerOutput output, Expression expected) {
        final Expression actual = ParserProcessor.processParsing(output);
        assertExpression(expected, actual);
    }
    
    private static void assertExpression(Expression expected, Expression actual) {
            Class<?> clazz = Expression.class;
            Field fConstants;
            Field fActions;
            Field constantNumber;
            try {
                fConstants = clazz.getDeclaredField("constants");
                fConstants.setAccessible(true);
                Double[] constantsExpected = (Double[]) fConstants.get(expected);
                Double[] constantsActual = (Double[]) fConstants.get(actual);
                
                fActions = clazz.getDeclaredField("actions");
                fActions.setAccessible(true);
                Action[] actionsExpected = (Action[]) fActions.get(expected);
                Action[] actionsActual = (Action[]) fActions.get(actual);
                
                constantNumber = clazz.getDeclaredField("constantsNumber");
                constantNumber.setAccessible(true);
                int constNumExpected = constantNumber.getInt(expected);
                int constNumActual = constantNumber.getInt(actual);
                
                String[] argsExpected = expected.getArgumentNames();
                String[] argsActual = actual.getArgumentNames();
                
                Assert.assertEquals(expected.getArity(), actual.getArity());
                Assert.assertEquals(constNumExpected, constNumActual);
                
                Assert.assertArrayEquals(constantsExpected, constantsActual);
                Assert.assertArrayEquals(argsExpected, argsActual);
                               
                for (int i = 0; i < actionsActual.length; i++) {
                    Assert.assertArrayEquals("Failed arguments at: " + i,actionsExpected[i].getArgumentList(),
                            actionsActual[i].getArgumentList());
                    Assert.assertTrue("Failed actions at: " + i,actionsExpected[i].getAction()
                            == actionsActual[i].getAction());
                }
            }
            catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
    }

}
