package attatrol.exparser;

import static org.junit.Assert.fail;
import static attatrol.exparser.utility.ExpressionStaticHolder.EXPR_SIMPLE_1;
import static attatrol.exparser.utility.LexerOutputStaticHolder.INITIALIZER;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import attatrol.exparser.BadExpressionException;
import attatrol.exparser.parser.Action;
import attatrol.exparser.parser.Expression;

public class ExpressionParserTest
{
    @Test
    public void testParser()
    {
        testParser(EXPR_SIMPLE_1, "1+2");
    }
    
    private static void testParser(Expression expected, String input) {
        try {
            assertExpression(expected, INITIALIZER.parse(input));
        }
        catch (BadExpressionException e) {
            e.printStackTrace();
            fail("Bad input");
        }
    }
    
    private static void assertExpression(Expression expected, Expression actual) {
        Class<?> clazz = Expression.class;
        Field fConstants;
        Field fActions;
        Field constantNumber;
        try {
            fConstants = clazz.getDeclaredField("constants");
            fConstants.setAccessible(true);
            double[] constantsExpected = (double[]) fConstants.get(expected);
            double[] constantsActual = (double[]) fConstants.get(actual);
            
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
            
            Assert.assertArrayEquals(constantsExpected, constantsActual, 0.00001);
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
