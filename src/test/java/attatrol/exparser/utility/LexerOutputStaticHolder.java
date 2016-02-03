package attatrol.exparser.utility;

import static attatrol.exparser.ExpressionParser.COMMA;
import static attatrol.exparser.ExpressionParser.LEFT_PARENTHESIS;
import static attatrol.exparser.ExpressionParser.RIGHT_PARENTHESIS;
import static attatrol.exparser.utility.ExampleExpressionParserFactory.*;

import java.util.ArrayList;
import java.util.List;

import attatrol.exparser.ExpressionParser;
import attatrol.exparser.lexer.Lexeme;
import attatrol.exparser.lexer.LexerOutput;
import attatrol.exparser.tokens.Argument;
import attatrol.exparser.tokens.Constant;

public class LexerOutputStaticHolder
{
    private LexerOutputStaticHolder() {
        
    }
    
    public static final ExpressionParser INITIALIZER =
            ExampleExpressionParserFactory.getInitializer();
    
    //1+2
    public static final LexerOutput SIMPLE_1;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, new Constant(1.)));
        lexemes.add(new Lexeme(1, 1, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(2, 2, new Constant(2.)));

        SIMPLE_1 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    //(a+2)*b
    public static final LexerOutput SIMPLE_2;
    static {
        List<Argument> arguments = new ArrayList<>();
        final Argument arg0 = new Argument("a");
        final Argument arg1 = new Argument("b");
        arguments.add(arg0);
        arguments.add(arg1);
        
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(1, 1, arg0));
        lexemes.add(new Lexeme(2, 2, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(3, 3, new Constant(2.)));
        lexemes.add(new Lexeme(4, 4, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(5, 5, INITIALIZER.getInfixOperation(MULTIPLY)));
        lexemes.add(new Lexeme(6, 6, arg1));

        SIMPLE_2 = new LexerOutput(lexemes, arguments);
    }
    //max(-a<<,-b<<)
    public static final LexerOutput SIMPLE_3;
    static {
        List<Argument> arguments = new ArrayList<>();
        final Argument arg0 = new Argument("a");
        final Argument arg1 = new Argument("b");
        arguments.add(arg0);
        arguments.add(arg1);
        
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 2, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(3, 3, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(4, 4, INITIALIZER.getPrefixOperation(MINUS)));
        lexemes.add(new Lexeme(5, 5, arg0));
        lexemes.add(new Lexeme(6, 7, INITIALIZER.getPostfixOperation(SHIFT)));        
        lexemes.add(new Lexeme(8, 8, COMMA));
        lexemes.add(new Lexeme(9, 9, INITIALIZER.getPrefixOperation(MINUS)));
        lexemes.add(new Lexeme(10, 10, arg1));
        lexemes.add(new Lexeme(11, 12, INITIALIZER.getPostfixOperation(SHIFT))); 
        lexemes.add(new Lexeme(13, 13, RIGHT_PARENTHESIS));

        SIMPLE_3 = new LexerOutput(lexemes, arguments);
    }
    
    //pi()
    public static final LexerOutput SIMPLE_4;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 1, INITIALIZER.getFunction(PI)));
        lexemes.add(new Lexeme(2, 2, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(3, 3, RIGHT_PARENTHESIS));

        SIMPLE_4 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    //max (((a+b)%(c+d)),(a+c))
    public static final LexerOutput SIMPLE_5;
    static {
        List<Argument> arguments = new ArrayList<>();
        final Argument arg0 = new Argument("a");
        final Argument arg1 = new Argument("b");
        final Argument arg2 = new Argument("c");
        final Argument arg3 = new Argument("d");
        arguments.add(arg0);
        arguments.add(arg1);
        arguments.add(arg2);
        arguments.add(arg3);
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 2, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(4, 4, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(5, 5, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(6, 6, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(7, 7, arg0));
        lexemes.add(new Lexeme(8, 8, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(9, 9, arg1));
        lexemes.add(new Lexeme(10, 10, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(11, 11, INITIALIZER.getInfixOperation(MOD)));
        lexemes.add(new Lexeme(12, 12, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(13, 13, arg2));
        lexemes.add(new Lexeme(14, 14, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(15, 15, arg3));
        lexemes.add(new Lexeme(16, 16, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(17, 17, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(18, 18, COMMA));
        lexemes.add(new Lexeme(19, 19, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(20, 20, arg0));
        lexemes.add(new Lexeme(21, 21, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(22, 22, arg2));
        lexemes.add(new Lexeme(23, 23, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(24, 24, RIGHT_PARENTHESIS));

        SIMPLE_5 = new LexerOutput(lexemes, arguments);
    }
    //-25.6+100.32-12/  7
    public static final LexerOutput SIMPLE_6;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, INITIALIZER.getPrefixOperation(MINUS)));
        lexemes.add(new Lexeme(1, 4, new Constant(25.6)));
        lexemes.add(new Lexeme(5, 5, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(6, 11, new Constant(100.32)));
        lexemes.add(new Lexeme(12, 12, INITIALIZER.getInfixOperation(MINUS)));
        lexemes.add(new Lexeme(13, 14, new Constant(12.)));
        lexemes.add(new Lexeme(15, 15, INITIALIZER.getInfixOperation(DIVIDE)));
        lexemes.add(new Lexeme(18, 18, new Constant(7.)));

        SIMPLE_6 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    
    //max(122, 12.4) - +(1*3+sqrt(12+5)*-(4.6/3.1))
    public static final LexerOutput COMPLEX_1;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 2, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(3, 3, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(4, 6, new Constant(122.)));
        lexemes.add(new Lexeme(7, 7, COMMA));
        lexemes.add(new Lexeme(9, 12, new Constant(12.4)));
        lexemes.add(new Lexeme(13, 13, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(15, 15, INITIALIZER.getInfixOperation(MINUS)));
        lexemes.add(new Lexeme(17, 17, INITIALIZER.getPrefixOperation(PLUS)));
        lexemes.add(new Lexeme(18, 18, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(19, 19, new Constant(1.)));
        lexemes.add(new Lexeme(20, 20, INITIALIZER.getInfixOperation(MULTIPLY)));
        lexemes.add(new Lexeme(21, 21, new Constant(3.)));
        lexemes.add(new Lexeme(22, 22, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(23, 26, INITIALIZER.getFunction(SQRT)));
        lexemes.add(new Lexeme(27, 27, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(28, 29, new Constant(12.)));
        lexemes.add(new Lexeme(30, 30, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(31, 31, new Constant(5.)));
        lexemes.add(new Lexeme(32, 32, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(33, 33, INITIALIZER.getInfixOperation(MULTIPLY)));
        lexemes.add(new Lexeme(34, 34, INITIALIZER.getPrefixOperation(MINUS)));
        lexemes.add(new Lexeme(35, 35, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(36, 38, new Constant(4.6)));
        lexemes.add(new Lexeme(39, 39, INITIALIZER.getInfixOperation(DIVIDE)));
        lexemes.add(new Lexeme(40, 42, new Constant(3.1)));
        lexemes.add(new Lexeme(43, 43, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(44, 44, RIGHT_PARENTHESIS));

        COMPLEX_1 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    
    //anthony1*anthony2/max(anthony1-2, anthony2-2)<<-13.4+sqrt(d) - -c<<<< +6<<
    public static final LexerOutput COMPLEX_2;
    static {
        List<Argument> arguments = new ArrayList<>();
        final Argument arg0 = new Argument("anthony1");
        final Argument arg1 = new Argument("anthony2");
        final Argument arg2 = new Argument("d");
        final Argument arg3 = new Argument("c");
        arguments.add(arg0);
        arguments.add(arg1);
        arguments.add(arg2);
        arguments.add(arg3);
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 7, arg0));
        lexemes.add(new Lexeme(8, 8, INITIALIZER.getInfixOperation(MULTIPLY)));
        lexemes.add(new Lexeme(9, 16, arg1));
        lexemes.add(new Lexeme(17, 17, INITIALIZER.getInfixOperation(DIVIDE)));
        lexemes.add(new Lexeme(18, 20, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(21, 21, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(22, 29, arg0));
        lexemes.add(new Lexeme(30, 30, INITIALIZER.getInfixOperation(MINUS)));
        lexemes.add(new Lexeme(31, 31, new Constant(2.)));
        lexemes.add(new Lexeme(32, 32, COMMA));
        lexemes.add(new Lexeme(34, 41, arg1));
        lexemes.add(new Lexeme(42, 42, INITIALIZER.getInfixOperation(MINUS)));
        lexemes.add(new Lexeme(43, 43, new Constant(2.)));
        lexemes.add(new Lexeme(44, 44, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(45, 46, INITIALIZER.getPostfixOperation(SHIFT)));
        lexemes.add(new Lexeme(47, 47, INITIALIZER.getInfixOperation(MINUS)));
        lexemes.add(new Lexeme(48, 51, new Constant(13.4)));
        lexemes.add(new Lexeme(52, 52, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(53, 56, INITIALIZER.getFunction(SQRT)));
        lexemes.add(new Lexeme(57, 57, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(58, 58, arg2));
        lexemes.add(new Lexeme(59, 59, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(61, 61, INITIALIZER.getInfixOperation(MINUS)));
        lexemes.add(new Lexeme(63, 63, INITIALIZER.getPrefixOperation(MINUS)));
        lexemes.add(new Lexeme(64, 64, arg3));
        lexemes.add(new Lexeme(65, 66, INITIALIZER.getPostfixOperation(SHIFT)));
        lexemes.add(new Lexeme(67, 68, INITIALIZER.getPostfixOperation(SHIFT)));
        lexemes.add(new Lexeme(70, 70 , INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(71, 71, new Constant(6.)));
        lexemes.add(new Lexeme(72, 73, INITIALIZER.getPostfixOperation(SHIFT)));

        COMPLEX_2 = new LexerOutput(lexemes, arguments);
    }

    //---max(max(max(max(max(a,b), max(c, d)), 1),23b  <<<<  <<<<), --c+d/(a+b))<<<<
    public static final LexerOutput COMPLEX_3;
    static {
        List<Argument> arguments = new ArrayList<>();
        final Argument arg0 = new Argument("a");
        final Argument arg1 = new Argument("b");
        final Argument arg2 = new Argument("c");
        final Argument arg3 = new Argument("d");
        final Argument arg4 = new Argument("23b");
        arguments.add(arg0);
        arguments.add(arg1);
        arguments.add(arg2);
        arguments.add(arg3);
        arguments.add(arg4);
        
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, INITIALIZER.getPrefixOperation(MINUS)));
        lexemes.add(new Lexeme(1, 1, INITIALIZER.getPrefixOperation(MINUS)));
        lexemes.add(new Lexeme(2, 2, INITIALIZER.getPrefixOperation(MINUS)));
        lexemes.add(new Lexeme(3, 5, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(6, 6, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(7, 9, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(10, 10, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(11, 13, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(14, 14, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(15, 17, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(18, 18, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(19, 21, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(22, 22, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(23, 23, arg0));
        lexemes.add(new Lexeme(24, 24, COMMA));
        lexemes.add(new Lexeme(25, 25, arg1));
        lexemes.add(new Lexeme(26, 26, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(27, 27, COMMA));
        lexemes.add(new Lexeme(29, 31, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(32, 32, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(33, 33, arg2));
        lexemes.add(new Lexeme(34, 34, COMMA));
        lexemes.add(new Lexeme(36, 36, arg3));
        lexemes.add(new Lexeme(37, 37, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(38, 38, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(39, 39, COMMA));
        lexemes.add(new Lexeme(41, 41, new Constant(1.)));
        lexemes.add(new Lexeme(42, 42, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(43, 43, COMMA));
        lexemes.add(new Lexeme(44, 46, arg4));
        lexemes.add(new Lexeme(49, 50, INITIALIZER.getPostfixOperation(SHIFT)));
        lexemes.add(new Lexeme(51, 52, INITIALIZER.getPostfixOperation(SHIFT)));
        lexemes.add(new Lexeme(55, 56, INITIALIZER.getPostfixOperation(SHIFT)));
        lexemes.add(new Lexeme(57, 58, INITIALIZER.getPostfixOperation(SHIFT)));
        lexemes.add(new Lexeme(59, 59, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(60, 60, COMMA));
        lexemes.add(new Lexeme(62, 62, INITIALIZER.getPrefixOperation(MINUS)));
        lexemes.add(new Lexeme(63, 63, INITIALIZER.getPrefixOperation(MINUS)));
        lexemes.add(new Lexeme(64, 64, arg2));
        lexemes.add(new Lexeme(65, 65, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(66, 66, arg3));
        lexemes.add(new Lexeme(67, 67, INITIALIZER.getInfixOperation(DIVIDE)));
        lexemes.add(new Lexeme(68, 68, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(69, 69, arg0));
        lexemes.add(new Lexeme(70, 70, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(71, 71, arg1));
        lexemes.add(new Lexeme(72, 72, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(73, 73, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(74, 75, INITIALIZER.getPostfixOperation(SHIFT)));
        lexemes.add(new Lexeme(76, 77, INITIALIZER.getPostfixOperation(SHIFT))); 
        COMPLEX_3 = new LexerOutput(lexemes, arguments);
    }
    
  //a
    public static final LexerOutput TRIVIAL_1;
    static {
        List<Argument> arguments = new ArrayList<>();
        final Argument arg0 = new Argument("a");
        arguments.add(arg0);
        
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, arg0));

        TRIVIAL_1 = new LexerOutput(lexemes, arguments);
    }
    
  //1
    public static final LexerOutput TRIVIAL_2;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, new Constant(1.)));

        TRIVIAL_2 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
}
