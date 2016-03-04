package attatrol.exparser.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import attatrol.exparser.ExpressionParser;
import attatrol.exparser.tokens.Function;
import attatrol.exparser.tokens.InfixOperation;
import attatrol.exparser.tokens.PostfixOperation;
import attatrol.exparser.tokens.PrefixOperation;
import attatrol.exparser.tokens.Token;
import attatrol.exparser.tokens.WrongArgumentTypeException;

public class ExampleExpressionParserFactory
{
    public static final String PLUS = "+";
    public static final String MINUS = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";
    public static final String MOD = "%";
    public static final String SHIFT = "<<";
    public static final String SQRT = "sqrt";
    public static final String MAX = "max";
    public static final String PI = "pi";

    public static ExpressionParser getInitializer()
    {

        //FormulaParserInitializer init2 = new FormulaParserInitializer(null);
        Map<String, Function> functionLookup = new HashMap<>();
        Map<String, InfixOperation> infixOperationLookup = new HashMap<>();
        Map<String, PrefixOperation> prefixOperationLookup = new HashMap<>();
        Map<String, PostfixOperation> postfixOperationLookup = new HashMap<>();

        //functionLookup
        functionLookup.put(SQRT, new Function(1)
        {

            @Override
            protected Object innerCalculate(Object[] args) throws WrongArgumentTypeException
            {
                if (!(args[0] instanceof Double)) {
                    throw new WrongArgumentTypeException(args[0], this);
                }
                return Math.sqrt((Double) args[0]);
            }
        });

        functionLookup.put(MAX, new Function(2)
        {

            @Override
            protected Object innerCalculate(Object[] args)throws WrongArgumentTypeException
            {
                if (!(args[0] instanceof Double)) {
                    throw new WrongArgumentTypeException(args[0], this);
                }
                else if (!(args[1] instanceof Double)) {
                    
                }
                final Double arg0 = (Double) args[0];
                final Double arg1 = (Double) args[1];
                return arg0 > arg1 ? args[0] : args[1];
            }
        });

        functionLookup.put(PI, new Function(0)
        {

            @Override
            protected Object innerCalculate(Object[] args) throws WrongArgumentTypeException
            {
                return Double.valueOf(3.14);
            }
        });

        //infixOperationLookup
        infixOperationLookup.put(PLUS, new InfixOperation(0)
        {
            @Override
            public Object calculate(Object arg1, Object arg2) throws WrongArgumentTypeException
            {
                if (!(arg1 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg1, this);
                }
                if (!(arg2 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg2, this);
                }                
                return (Double) arg1 + (Double) arg2;
            }
        });

        infixOperationLookup.put(MINUS, new InfixOperation(0)
        {
            @Override
            public Object calculate(Object arg1, Object arg2) throws WrongArgumentTypeException
            {
                if (!(arg1 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg1, this);
                }
                if (!(arg2 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg2, this);
                }  
                return (Double) arg1 - (Double) arg2;
            }
        });

        infixOperationLookup.put(DIVIDE, new InfixOperation(1)
        {
            @Override
            public Object calculate(Object arg1, Object arg2) throws WrongArgumentTypeException
            {
                if (!(arg1 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg1, this);
                }
                if (!(arg2 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg2, this);
                }  
                return (Double) arg1 / (Double) arg2;
            }
        });

        infixOperationLookup.put(MULTIPLY, new InfixOperation(1)
        {
            @Override
            public Object calculate(Object arg1, Object arg2) throws WrongArgumentTypeException
            {
                if (!(arg1 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg1, this);
                }
                if (!(arg2 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg2, this);
                }  
                return (Double) arg1 * (Double) arg2;
            }
        });

        infixOperationLookup.put(MOD, new InfixOperation(1)
        {
            @Override
            public Object calculate(Object arg1, Object arg2) throws WrongArgumentTypeException
            {
                if (!(arg1 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg1, this);
                }
                if (!(arg2 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg2, this);
                } 
                return (Double) arg1 % (Double) arg2;
            }
        });

        //prefixOperationLookup
        prefixOperationLookup.put(PLUS, new PrefixOperation()
        {
            @Override
            public Object calculate(Object arg1) throws WrongArgumentTypeException
            {
                if (!(arg1 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg1, this);
                }
                return arg1;
            }
        });

        prefixOperationLookup.put(MINUS, new PrefixOperation()
        {
            @Override
            public Object calculate(Object arg1) throws WrongArgumentTypeException
            {
                if (!(arg1 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg1, this);
                }
                return - (Double) arg1;
            }
        });

        //postfixOperationLookup
        //this is artificial operation 'left shift'
        postfixOperationLookup.put(SHIFT, new PostfixOperation()
        {
            @Override
            public Object calculate(Object arg1) throws WrongArgumentTypeException
            {
                if (!(arg1 instanceof Double)) {
                    throw new WrongArgumentTypeException(arg1, this);
                }
                return (Double) arg1 * 2;
            }
        });

        return new ExpressionParser(functionLookup, infixOperationLookup,
                prefixOperationLookup, postfixOperationLookup);

    }

}
