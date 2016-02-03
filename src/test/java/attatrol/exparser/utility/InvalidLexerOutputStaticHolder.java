package attatrol.exparser.utility;

import static attatrol.exparser.ExpressionParser.*;
import static attatrol.exparser.utility.ExampleExpressionParserFactory.*;
import static attatrol.exparser.utility.LexerOutputStaticHolder.INITIALIZER;

import java.util.ArrayList;
import java.util.List;

import attatrol.exparser.lexer.Lexeme;
import attatrol.exparser.lexer.LexerOutput;
import attatrol.exparser.tokens.Argument;
import attatrol.exparser.tokens.Constant;


public class InvalidLexerOutputStaticHolder
{
    private InvalidLexerOutputStaticHolder() {
        
    }
    public static final LexerOutput EMPTY = new LexerOutput
            (new ArrayList<Lexeme>(), new ArrayList<Argument>());

    //"()( )( (   )) ()"
    public static final LexerOutput VALID_PARENTHESES;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(1, 1, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(2, 2, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(4, 4, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(5, 5, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(7, 7, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(11, 11, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(12, 12, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(14, 14, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(15, 15, RIGHT_PARENTHESIS));

        VALID_PARENTHESES = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    //"("
    public static final LexerOutput SINGLE_LEFT_PARENTHESIS;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, LEFT_PARENTHESIS));

        SINGLE_LEFT_PARENTHESIS = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    
    //")"
    public static final LexerOutput SINGLE_RIGHT_PARENTHESIS;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, RIGHT_PARENTHESIS));

        SINGLE_RIGHT_PARENTHESIS = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    
    //"())("
    public static final LexerOutput WRONG_ORDER_PARENTHESES;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(1, 1, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(2, 2, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(3, 3, LEFT_PARENTHESIS));

        WRONG_ORDER_PARENTHESES = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    
    //"a b"
    public static final LexerOutput BAD_INTEGRITY_1;
    static {
        List<Argument> arguments = new ArrayList<>();
        final Argument arg0 = new Argument("a");
        final Argument arg1 = new Argument("b");
        arguments.add(arg0);
        arguments.add(arg1);
        
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, arg0));
        lexemes.add(new Lexeme(2, 2, arg1));

        BAD_INTEGRITY_1 = new LexerOutput(lexemes, arguments);
    }
    
    //"1 2"
    public static final LexerOutput BAD_INTEGRITY_2;
    static {      
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, new Constant(1.)));
        lexemes.add(new Lexeme(2, 2, new Constant(2.)));

        BAD_INTEGRITY_2 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }

    //")("
    public static final LexerOutput BAD_INTEGRITY_3;
    static {      
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(1, 1, LEFT_PARENTHESIS));

        BAD_INTEGRITY_3 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    
    //"4 ("
    public static final LexerOutput BAD_INTEGRITY_4;
    static {      
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 0, new Constant(4.)));
        lexemes.add(new Lexeme(2, 2, LEFT_PARENTHESIS));

        BAD_INTEGRITY_4 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    //max(,)+()+sqrt()
    public static final LexerOutput MEANINGLESS_1;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 2, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(3, 3, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(4, 4, COMMA));
        lexemes.add(new Lexeme(5, 5, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(6, 6, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(7, 7, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(8, 8, RIGHT_PARENTHESIS));
        lexemes.add(new Lexeme(9, 9, INITIALIZER.getInfixOperation(PLUS)));
        lexemes.add(new Lexeme(10, 13, INITIALIZER.getFunction(SQRT)));
        lexemes.add(new Lexeme(14, 14, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(15, 15, RIGHT_PARENTHESIS));

        MEANINGLESS_1 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    //pi(9)
    public static final LexerOutput BAD_FUNCTION_1;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 1, INITIALIZER.getFunction(PI)));
        lexemes.add(new Lexeme(2, 2, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(3, 3, COMMA));
        lexemes.add(new Lexeme(4, 4, new Constant(9.)));
        lexemes.add(new Lexeme(5, 5, RIGHT_PARENTHESIS));

        BAD_FUNCTION_1 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    //max(,1)
    public static final LexerOutput BAD_FUNCTION_2;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 2, INITIALIZER.getFunction(MAX)));
        lexemes.add(new Lexeme(3, 3, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(4, 4, COMMA));
        lexemes.add(new Lexeme(5, 5, new Constant(1.)));
        lexemes.add(new Lexeme(6, 6, RIGHT_PARENTHESIS));

        BAD_FUNCTION_2 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    //sqrt()
    public static final LexerOutput BAD_FUNCTION_3;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 3, INITIALIZER.getFunction(SQRT)));
        lexemes.add(new Lexeme(4, 4, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(5, 5, RIGHT_PARENTHESIS));

        BAD_FUNCTION_3 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    //sqrt)
    public static final LexerOutput BAD_FUNCTION_4;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 3, INITIALIZER.getFunction(SQRT)));
        lexemes.add(new Lexeme(4, 4, RIGHT_PARENTHESIS));

        BAD_FUNCTION_4 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    //pi(
    public static final LexerOutput BAD_FUNCTION_5;
    static {
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 1, INITIALIZER.getFunction(PI)));
        lexemes.add(new Lexeme(2, 2, LEFT_PARENTHESIS));

        BAD_FUNCTION_5 = new LexerOutput(lexemes, new ArrayList<Argument>());
    }
    //max(a,)
    public static final LexerOutput BAD_FUNCTION_6;
    static {
        List<Argument> arguments = new ArrayList<>();
        Argument arg0 = new Argument("a");
        arguments.add(arg0);
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 2, INITIALIZER.getFunction(SQRT)));
        lexemes.add(new Lexeme(3, 3, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(4, 4, arg0));
        lexemes.add(new Lexeme(5, 5, COMMA));
        lexemes.add(new Lexeme(6, 6, RIGHT_PARENTHESIS));

        BAD_FUNCTION_6 = new LexerOutput(lexemes, arguments);
    }
    //sqrt(+a
    public static final LexerOutput BAD_FUNCTION_7;
    static {
        List<Argument> arguments = new ArrayList<>();
        Argument arg0 = new Argument("a");
        arguments.add(arg0);
        List<Lexeme> lexemes = new ArrayList<>();
        lexemes.add(new Lexeme(0, 3, INITIALIZER.getFunction(SQRT)));
        lexemes.add(new Lexeme(4, 4, LEFT_PARENTHESIS));
        lexemes.add(new Lexeme(5, 5, INITIALIZER.getPrefixOperation(PLUS)));
        lexemes.add(new Lexeme(6, 6, arg0));

        BAD_FUNCTION_7 = new LexerOutput(lexemes, arguments);
    }
    
}
