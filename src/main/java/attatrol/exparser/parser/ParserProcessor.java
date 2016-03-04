package attatrol.exparser.parser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Quartet;

import attatrol.exparser.lexer.Lexeme;
import attatrol.exparser.lexer.LexerOutput;
import attatrol.exparser.tokens.*;

/**
 * Produces Expression instance from a valid LexerOutput instance.
 * @author atta_troll
 */
public class ParserProcessor {
    /**
     * This values represent order of execution for all executive tokens. They
     * have to be different to produce stable results. Do not change them unless
     * you are sure what you are doing. If executive tokens are in the same
     * parentheses, these define their order of execution.
     */
    private static final int FUNCTION_POSITION_PRIORITY = Integer.MAX_VALUE;
    private static final int PREFIX_OP_POSITION_PRIORITY = 2000;
    private static final int INFIX_OP_POSITION_PRIORITY = 0;
    private static final int POSTFIX_OP_POSITION_PRIORITY = 1000;

    /**
     * Replaces commas during function processing.
     */
    private static final Lexeme BLANK_LEXEME = new Lexeme(0, 0, null);

    private ParserProcessor() {

    }

    /**
     * Sorts all executive tokens in the order of valid execution
     * @author atta_troll
     */
    private static class OrderOfExecutionComparator
            implements Comparator<Quartet<Token, Integer, Integer, Integer>> {

        @Override
        public int compare(Quartet<Token, Integer, Integer, Integer> arg0,
                Quartet<Token, Integer, Integer, Integer> arg1) {
            int result = arg1.getValue1() - arg0.getValue1();
            if (result != 0) {
                return result;
            } else {
                result = arg1.getValue2() - arg0.getValue2();
                if (result != 0) {
                    return result;
                } else {
                    result = arg1.getValue3() - arg0.getValue3();
                    // prefix operations are executed from right to left
                    if (arg0.getValue0() instanceof PrefixOperation
                            && arg1.getValue0() instanceof PrefixOperation) {
                        return result;
                    } else {
                        return -result;
                    }
                }

            }
        }

    }

    /**
     * Produces Expression from a LexerOutput instance. Last must be a verified
     * one.
     * @param lexerOutput
     * @return
     */
    public static Expression processParsing(LexerOutput lexerOutput) {
        List<Argument> arguments = lexerOutput.getArguments();
        List<Lexeme> lexemes = lexerOutput.getLexemes();

        // form index map for arguments
        Map<Argument, Integer> argumentLookup = getArgumentLookup(arguments);

        // work out arguments
        final String[] argumentNames = getArgumentNames(arguments);
        final int arity = argumentNames.length;
        final int constantNumber = getConstantNumber(lexemes);
        final int intermediateValueOffset = constantNumber + arity;

        // prepare containers for parsed data
        List<Double> constants = new ArrayList<>();
        List<Action> actions = new ArrayList<>();

        // execute parsing
        List<Quartet<Token, Integer, Integer, Integer>> sortedActions =
                getActionsSortedByExecutivePriority(lexemes);
        for (Quartet<Token, Integer, Integer, Integer> executiveToken : sortedActions) {
            final Token token = executiveToken.getValue0();
            final int tokenCoord = executiveToken.getValue3();
            final int startCoord;
            final int endCoord;
            final int[] args;
            if (token instanceof Function) {
                final Function func = (Function) token;
                final int funcArity = func.getArity();
                startCoord = tokenCoord;
                endCoord = getLastCoordinateOfFunction(lexemes, tokenCoord + 2);
                args = new int[funcArity];
                if (funcArity > 0) {
                    int[] argCoords = resolveFunctionArgumentCoordinates(lexemes, tokenCoord + 2,
                            funcArity);
                    for (int i = 0; i < argCoords.length; i++) {
                        args[i] = resolveArgument(lexemes, argCoords[i], arity, argumentLookup,
                                constants);
                    }
                }
                functionFillCommas(lexemes, tokenCoord + 1, endCoord - 1);
                ;
            } else if (token instanceof InfixOperation) {
                startCoord = getCoordRightToLeft(lexemes, tokenCoord - 1);
                endCoord = getCoordLeftToRight(lexemes, tokenCoord + 1);
                args = new int[2];
                args[0] = resolveArgument(lexemes, startCoord, arity, argumentLookup, constants);
                args[1] = resolveArgument(lexemes, endCoord, arity, argumentLookup, constants);
            } else if (token instanceof PrefixOperation) {
                startCoord = tokenCoord;
                endCoord = getCoordLeftToRight(lexemes, tokenCoord + 1);
                args = new int[1];
                args[0] = resolveArgument(lexemes, endCoord, arity, argumentLookup, constants);
            } else {
                startCoord = getCoordRightToLeft(lexemes, tokenCoord - 1);
                endCoord = tokenCoord;
                args = new int[1];
                args[0] = resolveArgument(lexemes, startCoord, arity, argumentLookup, constants);

            }
            IntermediateValue iv = new IntermediateValue(intermediateValueOffset + actions.size());
            setIntermediateValue(lexemes, iv, startCoord, endCoord);
            actions.add(new Action(args, token));
        }

        // result formation
        Double[] constantsArray = new Double[constantNumber];
        if (actions.size() != 0) {
            for (int i = 0; i < constantNumber; i++) {
                constantsArray[i] = constants.get(i);
            }
        } else {
            if (constantNumber != 0) {
                constantsArray[0] = ((Constant) lexemes.get(0).getToken()).getValue();
            }
        }
        return new Expression(argumentNames, constantsArray, actions.toArray(new Action[0]), arity,
                constantNumber);

    }

    private static Map<Argument, Integer> getArgumentLookup(List<Argument> arguments) {
        Map<Argument, Integer> lookup = new HashMap<>();
        for (int i = 0; i < arguments.size(); i++) {
            lookup.put(arguments.get(i), i);
        }
        return lookup;
    }

    private static String[] getArgumentNames(List<Argument> arguments) {
        String[] argumentNames = new String[arguments.size()];
        for (int i = 0; i < arguments.size(); i++) {
            argumentNames[i] = arguments.get(i).getDesignation();
        }
        return argumentNames;
    }

    private static int getConstantNumber(List<Lexeme> lexemes) {
        int number = 0;
        for (Lexeme lexeme : lexemes) {
            if (lexeme.getToken() instanceof Constant) {
                number++;
            }
        }
        return number;
    }

    private static int getLastCoordinateOfFunction(List<Lexeme> lexemes, int value3) {
        for (int i = value3;; i++) {
            final Token token = lexemes.get(i).getToken();
            if (token instanceof RightParenthesis) {
                return i;
            }
        }
    }

    private static int[] resolveFunctionArgumentCoordinates(List<Lexeme> lexemes, int index,
            int arity) {
        int[] coords = new int[arity];
        int foundNumber = 0;
        boolean needArg = true;
        for (int i = index;; i++) {
            if (needArg && isValidArgument(lexemes.get(i))) {
                needArg = false;
                coords[foundNumber] = i;
                foundNumber++;
            }
            if (lexemes.get(i).getToken() instanceof Comma) {
                needArg = true;
            }
            if (foundNumber == arity) {
                return coords;
            }
        }
    }

    private static int resolveArgument(List<Lexeme> lexemes, int index, int arity,
            Map<Argument, Integer> argumentLookup, List<Double> constants) {
        final Lexeme lexeme = lexemes.get(index);
        if (lexeme instanceof IntermediateValue) {
            return ((IntermediateValue) lexeme).getIndex();
        } else {
            final Token token = lexeme.getToken();
            if (token instanceof Argument) {
                return argumentLookup.get((Argument) token);
            } else {
                Double constant = ((Constant) token).getValue();
                final int result = constants.size() + arity;
                constants.add(constant);
                return result;
            }
        }
    }

    private static void functionFillCommas(List<Lexeme> lexemes, int start, int end) {
        for (int i = start; i <= end; i++) {
            lexemes.set(i, BLANK_LEXEME);
        }

    }

    private static int getCoordLeftToRight(List<Lexeme> lexemes, int index) {
        final Lexeme lexeme = lexemes.get(index);
        final Token token = lexeme.getToken();
        if (token instanceof Argument || token instanceof Constant) {
            return index;
        } else {
            for (int i = index + 1;; i++) {
                if (lexeme == lexemes.get(i)) {
                    return i;
                }
            }
        }
    }

    private static int getCoordRightToLeft(List<Lexeme> lexemes, int index) {
        final Lexeme lexeme = lexemes.get(index);
        final Token token = lexeme.getToken();
        if (token instanceof Argument || token instanceof Constant) {
            return index;
        } else {
            for (int i = index - 1;; i--) {
                if (lexeme == lexemes.get(i)) {
                    return i;
                }
            }
        }
    }

    private static void setIntermediateValue(List<Lexeme> lexemes, IntermediateValue iv,
            int startCoord, int endCoord) {
        if (startCoord != 0 && endCoord != lexemes.size() - 1
                && lexemes.get(startCoord - 1).getToken() instanceof LeftParenthesis
                && lexemes.get(endCoord + 1).getToken() instanceof RightParenthesis
                && (startCoord - 1 == 0
                        || !(lexemes.get(startCoord - 2).getToken() instanceof Function))) {
            setIntermediateValue(lexemes, iv, startCoord - 1, endCoord + 1);
        } else {
            lexemes.set(startCoord, iv);
            lexemes.set(endCoord, iv);
        }

    }

    private static List<Quartet<Token, Integer, Integer, Integer>>
            getActionsSortedByExecutivePriority(List<Lexeme> lexemes) {
        List<Quartet<Token, Integer, Integer, Integer>> sortedPriorities = new ArrayList<>();
        int parenthesisLevel = 0;
        for (int i = 0; i < lexemes.size(); i++) {
            final Token token = lexemes.get(i).getToken();
            if (token instanceof LeftParenthesis) {
                parenthesisLevel++;
            } else if (token instanceof RightParenthesis) {
                parenthesisLevel--;
            } else if (token instanceof Operation) {
                sortedPriorities.add(new Quartet<Token, Integer, Integer, Integer>(token,
                        parenthesisLevel, getOperationPositionalPriority(token), i));
            } else if (token instanceof Function) {
                sortedPriorities.add(new Quartet<Token, Integer, Integer, Integer>(token,
                        parenthesisLevel, FUNCTION_POSITION_PRIORITY, i));
            }
        }
        sortedPriorities.sort(new ParserProcessor.OrderOfExecutionComparator());

        return sortedPriorities;
    }

    private static int getOperationPositionalPriority(Token token) {
        if (token instanceof PrefixOperation) {
            return PREFIX_OP_POSITION_PRIORITY;
        } else if (token instanceof InfixOperation) {
            return INFIX_OP_POSITION_PRIORITY + ((InfixOperation) token).getPriority();
        } else {
            return POSTFIX_OP_POSITION_PRIORITY;
        }
    }

    private static boolean isValidArgument(Lexeme lexeme) {
        return lexeme instanceof IntermediateValue || lexeme.getToken() instanceof Argument
                || lexeme.getToken() instanceof Constant;
    }

}
