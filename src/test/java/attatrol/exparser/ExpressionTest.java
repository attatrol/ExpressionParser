package attatrol.exparser;

import static attatrol.exparser.utility.ExpressionStaticHolder.*;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import attatrol.exparser.parser.Expression;

public class ExpressionTest
{

    @Test
    public void testExpressions()
    {
        testExpression(EXPR_SIMPLE_1, new double[0], 3.);
        testExpression(EXPR_SIMPLE_2, new double[]{.1, 3.}, 6.3);
        testExpression(EXPR_SIMPLE_3, new double[]{9.1, 1.}, -2.);
        testExpression(EXPR_SIMPLE_4, new double[0], 3.14);
        testExpression(EXPR_SIMPLE_6, new double[0], 73.00571429);
        testExpression(EXPR_COMPLEX_1, new double[0], 125.1181567);
        testExpression(EXPR_TRIVIAL_1, new double[]{-4.5}, -4.5);
        testExpression(EXPR_TRIVIAL_2, new double[0], 1.);
        //anthony1*anthony2/max(anthony1-2, anthony2-2)<<-13.4+sqrt(d) - -c<<<< +6<<
        testExpression(EXPR_COMPLEX_2, new double[]{-1., 3., 4., -5.}, -20.9);
        //---max(max(max(max(max(a,b), max(c, d)), 1),23b  <<<<  <<<<), --c+d/(a+b))<<<<
        testExpression(EXPR_COMPLEX_3, new double[]{-4., 3., 2., -5., -4.}, -28.);
    }
    
    @Test
    public void testWrongArity() {
        try {
            EXPR_SIMPLE_1.calculate(new double[] {0.});
        } 
        catch (IllegalArgumentException e) {
            return;
        }
        fail("wrong arity argument passed through");
    }
    
    private static void testExpression(Expression expr, double[] args, double expected) {
        Assert.assertEquals(expected, expr.calculate(args), 0.000001);
    }

}
