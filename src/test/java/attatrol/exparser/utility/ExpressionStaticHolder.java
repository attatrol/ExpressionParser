package attatrol.exparser.utility;

import static attatrol.exparser.utility.ExampleExpressionParserFactory.*;
import static attatrol.exparser.utility.LexerOutputStaticHolder.INITIALIZER;

import attatrol.exparser.parser.Action;
import attatrol.exparser.parser.Expression;
import attatrol.exparser.tokens.Token;

public class ExpressionStaticHolder
{
    private ExpressionStaticHolder() {
        
    }
    
    //1+2
    public static final Expression EXPR_SIMPLE_1;
    static {
        double[] constants = new double[] {1., 2.};
        Action[] actions = new Action[] { new Action(new int[]{0,1}, INITIALIZER.getInfixOperation(PLUS))};
        EXPR_SIMPLE_1 = new Expression(new String[0], constants, actions, 0, 2);
    }
    
    //(a+2)*b
    public static final Expression EXPR_SIMPLE_2;
    static {
        double[] constants = new double[] {2.};
        String[] args = new String[] {"a", "b"};
        Action[] actions = new Action[] { new Action(new int[]{0,2}, INITIALIZER.getInfixOperation(PLUS)),
                                          new Action(new int[]{3,1}, INITIALIZER.getInfixOperation(MULTIPLY)),          
                                        };
        EXPR_SIMPLE_2 = new Expression(args, constants, actions, 2, 1);
    }
    
    //max(-a<<,-b<<)
    public static final Expression EXPR_SIMPLE_3;
    static {
        double[] constants = new double[0];
        String[] args = new String[] {"a", "b"};
        Action[] actions = new Action[] { new Action(new int[]{1}, INITIALIZER.getPrefixOperation(MINUS)),
                                          new Action(new int[]{0}, INITIALIZER.getPrefixOperation(MINUS)),
                                          new Action(new int[]{3}, INITIALIZER.getPostfixOperation(SHIFT)),
                                          new Action(new int[]{2}, INITIALIZER.getPostfixOperation(SHIFT)),
                                          new Action(new int[]{4,5}, INITIALIZER.getFunction(MAX)),
                                        };
        EXPR_SIMPLE_3 = new Expression(args, constants, actions, 2, 0);
    }
    
    //pi()
    public static final Expression EXPR_SIMPLE_4;
    static {
        Action[] actions = new Action[] { new Action(new int[0], INITIALIZER.getFunction(PI))};
        EXPR_SIMPLE_4 = new Expression(new String[0], new double[0], actions, 0, 0);
    }
    
    //max (((a+b)%(c+d)),(a+c))
    public static final Expression EXPR_SIMPLE_5;
    static {
        double[] constants = new double[0];
        String[] args = new String[] {"a", "b", "c", "d"};
        Action[] actions = new Action[] { 
                                          new Action(new int[]{0,1}, INITIALIZER.getInfixOperation(PLUS)),
                                          new Action(new int[]{2,3}, INITIALIZER.getInfixOperation(PLUS)),
                                          new Action(new int[]{4,5}, INITIALIZER.getInfixOperation(MOD)),
                                          new Action(new int[]{0,2}, INITIALIZER.getInfixOperation(PLUS)),
                                          new Action(new int[]{6,7}, INITIALIZER.getFunction(MAX)),

                                        };
        EXPR_SIMPLE_5 = new Expression(args, constants, actions, 4, 0);
    }
        
    //-25.6+100.32-12/  7
    public static final Expression EXPR_SIMPLE_6;
    static {
        double[] constants = new double[] {25.6, 12., 7., 100.32};
        String[] args = new String[0];
        Action[] actions = new Action[] { new Action(new int[]{0}, INITIALIZER.getPrefixOperation(MINUS)),
                                          new Action(new int[]{1,2}, INITIALIZER.getInfixOperation(DIVIDE)),
                                          new Action(new int[]{4,3}, INITIALIZER.getInfixOperation(PLUS)),
                                          new Action(new int[]{6,5}, INITIALIZER.getInfixOperation(MINUS)),
                                        };
        EXPR_SIMPLE_6 = new Expression(args, constants, actions, 0, 4);
    }
    

    //max(122, 12.4) - +(1*3+sqrt(12+5)*-(4.6/3.1))
    public static final Expression EXPR_COMPLEX_1;
    static {
        double[] constants = new double[] {4.6, 3.1, 12., 5., 1., 3., 122., 12.4};
        String[] args = new String[0];
        Action[] actions = new Action[] { new Action(new int[]{0,1}, INITIALIZER.getInfixOperation(DIVIDE)),
                                          new Action(new int[]{2,3}, INITIALIZER.getInfixOperation(PLUS)),
                                          new Action(new int[]{9}, INITIALIZER.getFunction(SQRT)),
                                          new Action(new int[]{8}, INITIALIZER.getPrefixOperation(MINUS)),
                                          new Action(new int[]{4,5}, INITIALIZER.getInfixOperation(MULTIPLY)),
                                          new Action(new int[]{10,11}, INITIALIZER.getInfixOperation(MULTIPLY)),
                                          new Action(new int[]{12,13}, INITIALIZER.getInfixOperation(PLUS)),                                          
                                          new Action(new int[]{6,7}, INITIALIZER.getFunction(MAX)),
                                          new Action(new int[]{14}, INITIALIZER.getPrefixOperation(PLUS)),
                                          new Action(new int[]{15,16}, INITIALIZER.getInfixOperation(MINUS)),
                                        };
        EXPR_COMPLEX_1  = new Expression(args, constants, actions, 0, 8);
    }

    //anthony1*anthony2/max(anthony1-2, anthony2-2)<<-13.4+sqrt(d) - -c<<<< +6<<
    public static final Expression EXPR_COMPLEX_2;
    static {
        double[] constants = new double[] {2., 2., 6., 13.4};
        String[] args = new String[] {"anthony1", "anthony2", "d", "c"};
        Action[] actions = new Action[] { new Action(new int[]{0,4}, INITIALIZER.getInfixOperation(MINUS)),
                                          new Action(new int[]{1,5}, INITIALIZER.getInfixOperation(MINUS)),
                                          new Action(new int[]{8,9}, INITIALIZER.getFunction(MAX)),
                                          new Action(new int[]{2}, INITIALIZER.getFunction(SQRT)),
                                          new Action(new int[]{3}, INITIALIZER.getPrefixOperation(MINUS)),
                                          new Action(new int[]{10}, INITIALIZER.getPostfixOperation(SHIFT)),
                                          new Action(new int[]{12}, INITIALIZER.getPostfixOperation(SHIFT)),
                                          new Action(new int[]{14}, INITIALIZER.getPostfixOperation(SHIFT)),
                                          new Action(new int[]{6}, INITIALIZER.getPostfixOperation(SHIFT)),
                                          new Action(new int[]{0,1}, INITIALIZER.getInfixOperation(MULTIPLY)),
                                          new Action(new int[]{17,13}, INITIALIZER.getInfixOperation(DIVIDE)),
                                          new Action(new int[]{18,7}, INITIALIZER.getInfixOperation(MINUS)),
                                          new Action(new int[]{19,11}, INITIALIZER.getInfixOperation(PLUS)),
                                          new Action(new int[]{20,15}, INITIALIZER.getInfixOperation(MINUS)),
                                          new Action(new int[]{21,16}, INITIALIZER.getInfixOperation(PLUS)),
                                        };
        EXPR_COMPLEX_2 = new Expression(args, constants, actions, 4, 4);
    }
                                                         
    //---max(max(max(max(max(a,b), max(c, d)), 1),23b  <<<<  <<<<), --c+d/(a+b))<<<<
    public static final Expression EXPR_COMPLEX_3;
    static {
        double[] constants = new double[]{1.};
        String[] args = new String[] {"a", "b", "c", "d", "23b"};
        Action[] actions = new Action[] { new Action(new int[]{0,1}, INITIALIZER.getFunction(MAX)),
                                          new Action(new int[]{2,3}, INITIALIZER.getFunction(MAX)),
                                          new Action(new int[]{6,7}, INITIALIZER.getFunction(MAX)),
                                          new Action(new int[]{8,5}, INITIALIZER.getFunction(MAX)),
                                          new Action(new int[]{4}, INITIALIZER.getPostfixOperation(SHIFT)),
                                          new Action(new int[]{10}, INITIALIZER.getPostfixOperation(SHIFT)),
                                          new Action(new int[]{11}, INITIALIZER.getPostfixOperation(SHIFT)),
                                          new Action(new int[]{12}, INITIALIZER.getPostfixOperation(SHIFT)),
                                          new Action(new int[]{0,1}, INITIALIZER.getInfixOperation(PLUS)),
                                          new Action(new int[]{9,13}, INITIALIZER.getFunction(MAX)),
                                          new Action(new int[]{2}, INITIALIZER.getPrefixOperation(MINUS)),
                                          new Action(new int[]{16}, INITIALIZER.getPrefixOperation(MINUS)),
                                          new Action(new int[]{3,14}, INITIALIZER.getInfixOperation(DIVIDE)),
                                          new Action(new int[]{17,18}, INITIALIZER.getInfixOperation(PLUS)),
                                          new Action(new int[]{15,19}, INITIALIZER.getFunction(MAX)),
                                          new Action(new int[]{20}, INITIALIZER.getPrefixOperation(MINUS)),
                                          new Action(new int[]{21}, INITIALIZER.getPrefixOperation(MINUS)),
                                          new Action(new int[]{22}, INITIALIZER.getPrefixOperation(MINUS)),
                                          new Action(new int[]{23}, INITIALIZER.getPostfixOperation(SHIFT)),
                                          new Action(new int[]{24}, INITIALIZER.getPostfixOperation(SHIFT)),
                                        };
        EXPR_COMPLEX_3 = new Expression(args, constants, actions, 5, 1);
    }
    
    //a
    public static final Expression EXPR_TRIVIAL_1;
    static {
        double[] constants = new double[0];
        String[] args = new String[] {"a"};
        Action[] actions = new Action[0];

        EXPR_TRIVIAL_1 = new Expression(args, constants, actions, 1, 0);
    }
    
    //1
    public static final Expression EXPR_TRIVIAL_2;
    static {
        double[] constants = new double[] {1.};
        String[] args = new String[0];
        Action[] actions = new Action[0];
        EXPR_TRIVIAL_2 = new Expression(args, constants, actions, 0, 1);
    }
}
