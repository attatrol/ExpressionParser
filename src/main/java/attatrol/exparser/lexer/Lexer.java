package attatrol.exparser.lexer;

import static attatrol.exparser.ExpressionParser.COMMA;
import static attatrol.exparser.ExpressionParser.COMMA_DSGN;
import static attatrol.exparser.ExpressionParser.LEFT_PARENTHESIS;
import static attatrol.exparser.ExpressionParser.LEFT_PARENTHESIS_DSGN;
import static attatrol.exparser.ExpressionParser.RIGHT_PARENTHESIS;
import static attatrol.exparser.ExpressionParser.RIGHT_PARENTHESIS_DSGN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javatuples.Pair;

import attatrol.exparser.BadExpressionException;
import attatrol.exparser.ExpressionParser;
import attatrol.exparser.tokens.Argument;
import attatrol.exparser.tokens.Constant;
import attatrol.exparser.tokens.Function;
import attatrol.exparser.tokens.InfixOperation;
import attatrol.exparser.tokens.PostfixOperation;
import attatrol.exparser.tokens.PrefixOperation;
import attatrol.exparser.tokens.Token; 

/**
 * <p>
 * Detects known tokens in the input stream, returns sequence of the lexemes and list of
 * unbounded arguments.
 * </p>
 * <p>
 * Performs several type of verification: 1. tries to define type of operation, 2. tries to find out
 * type of the function if ambiguity is encountered, then BadFormulaException is thrown.
 * </p>
 * <p>
 *  There are 6 stages of lexing, each of them highlights some type of lexeme from the raw text,
 * first 4 stages are instances of LexerProcessor class:</br>
 * 1. parentheses and commas processor</br>
 * 2. preliminary operation processor</br>
 * 3. constants processor</br>
 * 4. functions processor</br>
 * 5. highlighting of unbounded arguments and forming their list</br>
 * 6. defining of operation type
 *</p>
 * @author atta_troll
 */
public class Lexer
{
    /**
     * Pattern used to find float-point digital constants by CONSTANTS_PROCESSOR.
     */
    public static final Pattern CONSTANT_PATTERN = Pattern.compile("\\d*\\.*\\d+");
    
    private Lexer() {
        
    }

    /**
     * Finds and highlights parentheses and commas in the working sequence. 
     */
    private static final LexerProcessor PARENTHESIS_COMMA_PROCESSOR = new LexerProcessor()
    {
        public List<LexerDataUnit> process(UnprocessedDataUnit unit, ExpressionParser init)
        {
            boolean unchanged = true;
            List<LexerDataUnit> result = new ArrayList<>();
            final String rawData = unit.getData();
            final int offset = unit.getStart();
            int oldCut = 0;
            for (int i = 0; i < rawData.length(); i++) {
                final char symbol = rawData.charAt(i);
                if (symbol == LEFT_PARENTHESIS_DSGN) {
                    unchanged = false;
                    if (i != 0 && oldCut != i) {
                        result.add(new UnprocessedDataUnit(offset + oldCut, offset + i - 1,
                                rawData.substring(oldCut, i)));
                    }
                    result.add(new Lexeme(offset + i, offset + i, LEFT_PARENTHESIS));
                    oldCut = i + 1;
                }
                else if (symbol == RIGHT_PARENTHESIS_DSGN) {
                    unchanged = false;
                    if (i != 0 && oldCut != i) {
                        result.add(new UnprocessedDataUnit(offset + oldCut, offset + i - 1,
                                rawData.substring(oldCut, i)));
                    }
                    result.add(new Lexeme(offset + i, offset + i, RIGHT_PARENTHESIS));
                    oldCut = i + 1;
                }
                else if (symbol == COMMA_DSGN) {
                    unchanged = false;
                    if (i != 0 && oldCut != i) {
                        result.add(new UnprocessedDataUnit(offset + oldCut, offset + i - 1,
                                rawData.substring(oldCut, i)));
                    }
                    result.add(new Lexeme(offset + i, offset + i, COMMA));
                    oldCut = i + 1;
                }
            }
            if (unchanged) {
                result.add(unit);
            }
            else if (oldCut != rawData.length()) {
                result.add(
                        new UnprocessedDataUnit(offset + oldCut, rawData.length() + offset - 1,
                                rawData.substring(oldCut)));
            }
            return result;
        }
    };

    /**
     * Finds and highlights all operation signs in the working sequence. 
     */
    private static final LexerProcessor OPERATION_FINDER_PROCESSOR = new LexerProcessor()
    {
        public List<LexerDataUnit> process(UnprocessedDataUnit unit, ExpressionParser init)
        {
            Pattern pattern = formOperationsPattern(init);
            Matcher matcher = pattern.matcher(unit.getData());
            boolean unchanged = true;
            List<LexerDataUnit> result = new ArrayList<>();
            final String rawData = unit.getData();
            final int offset = unit.getStart();
            int oldCut = 0;
            while (matcher.find()) {
                unchanged = false;
                if (oldCut != matcher.start()) {
                    result.add(
                            new UnprocessedDataUnit(offset + oldCut, offset + matcher.start() - 1,
                                    rawData.substring(oldCut, matcher.start())));
                }
                result.add(new IncompleteOperationLexeme(offset + matcher.start(),
                        offset + matcher.end()-1, matcher.group()));
                oldCut = matcher.end();
            }
            if (unchanged) {
                result.add(unit);
            }
            else if (oldCut != rawData.length()) {
                result.add(
                        new UnprocessedDataUnit(offset + oldCut, offset + rawData.length() - 1,
                                rawData.substring(oldCut)));
            }
            return result;
        }

        private Pattern formOperationsPattern(ExpressionParser init)
        {
            StringBuilder sb = new StringBuilder("(");
            for (String name : init.getOperationNames()) {
                sb.append("\\Q").append(name).append("\\E|");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            return Pattern.compile(sb.toString());
        }
    };

    /**
     * Finds and highlights all digital constants in the working sequence. 
     */
    private static final LexerProcessor CONSTANT_PROCESSOR = new LexerProcessor()
    {
        public List<LexerDataUnit> process(UnprocessedDataUnit unit, ExpressionParser init)
        {
            final String data = unit.getData();
            Matcher matcher = CONSTANT_PATTERN.matcher(data);
            List<LexerDataUnit> result = new ArrayList<>(1);
            if (matcher.matches()) {
                result.add(new Lexeme(unit.getStart(), unit.getEnd(),
                        new Constant(Double.parseDouble(data))));
            }
            else {
                result.add(unit);
            }
            return result;
        }
    };
    
    /**
     * Finds and highlights all function designations in the working sequence. 
     */
    private static final LexerProcessor FUNCTION_PROCESSOR = new LexerProcessor()
    {
        public List<LexerDataUnit> process(UnprocessedDataUnit unit, ExpressionParser init)
        {
            final Function func = init.getFunction(unit.getData());
            List<LexerDataUnit> result = new ArrayList<>(1);
            if (func != null) {
                result.add(new Lexeme(unit.getStart(), unit.getEnd(), (Token) func));
            }
            else {
                result.add(unit);
            }
            return result;
        }
    };

    /**
     * Main executing method of the class, transforms raw string formula input into LexerOutput POJO,
     * which contains a list of unbounded arguments of the formula and a list of sequenced lexemes highlighted
     * in the formula. After lexing there is no work with raw text but with well-defined objects.
     * @param input raw formula input.
     * @param initializer the parser initializer.
     * @return LexerOutput POJO
     * @throws BadExpressionException thrown on failure to determine operation type.
     */
    public static LexerOutput lexing(String input, ExpressionParser initializer)
            throws BadExpressionException
    {
        // remove wthitespaces and form sequence of data units
        int lastCut = 0;
        List<LexerDataUnit> sequence = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            final char symbol = input.charAt(i);
            if (Character.isWhitespace(symbol)) {
                if (lastCut != i) {
                    sequence.add(
                            new UnprocessedDataUnit(lastCut, i - 1, input.substring(lastCut, i)));
                }
                lastCut = i + 1;
            }
        }
        if (lastCut != input.length()) {
            sequence.add(
                new UnprocessedDataUnit(lastCut, input.length() - 1, input.substring(lastCut)));
        }
        // execute sequence of passes that highlight basic delimiters, such as commas, parenthesis,
        // operation signs.
        sequence = passThroughProcessor(sequence, PARENTHESIS_COMMA_PROCESSOR, initializer);
        sequence = passThroughProcessor(sequence, OPERATION_FINDER_PROCESSOR, initializer);
        sequence = passThroughProcessor(sequence, CONSTANT_PROCESSOR, initializer);
        sequence = passThroughProcessor(sequence, FUNCTION_PROCESSOR, initializer);
        //
        Pair<List<Argument>, List<LexerDataUnit>> results = defineArguments(sequence, initializer);
        sequence = defineOperations(results.getValue1(), initializer);
        // form result POJO
        List<Lexeme> lexemes = new ArrayList<>(sequence.size());
        for (LexerDataUnit unit : sequence) {
            lexemes.add((Lexeme) unit);
        }
        return new LexerOutput(lexemes, results.getValue0());
    }

    private static List<LexerDataUnit>
            passThroughProcessor(List<LexerDataUnit> seq, LexerProcessor processor, ExpressionParser init)
    {
        List<LexerDataUnit> newSeq = new ArrayList<>();
        for (LexerDataUnit unit : seq) {
            if (unit instanceof UnprocessedDataUnit) {
                newSeq.addAll(processor.process((UnprocessedDataUnit) unit, init));
            }
            else {
                newSeq.add(unit);
            }
        }
        return newSeq;
    }

    /**
     * After multiple passes through processors there are still some UnprocessedDataUnits in the
     * working sequence. Those are treated as unbounded arguments and should be listed as Argument
     * objects.
     * @param seq
     *        the working sequence.
     * @param initializer
     *        the parser's initializer.
     * @return list of the arguments.
     */
    private static Pair<List<Argument>, List<LexerDataUnit>>
            defineArguments(List<LexerDataUnit> seq, ExpressionParser initializer)
    {
        Map<String, Argument> argsLookup = new HashMap<>();
        List<Argument> args = new ArrayList<>();
        List<LexerDataUnit> newSeq = new ArrayList<>();
        for (int i = 0; i < seq.size(); i++) {
            final LexerDataUnit unit = seq.get(i);
            if (unit instanceof UnprocessedDataUnit) {
                final String argumentName = ((UnprocessedDataUnit) unit).getData();
                Argument argument = argsLookup.get(argumentName);
                if (argument == null) {
                    argument = new Argument(argumentName);
                    argsLookup.put(argumentName, argument);
                    args.add(argument);
                }
                newSeq.add(new Lexeme(unit.getStart(), unit.getEnd(), argument));
            }
            else {
                newSeq.add(unit);
            }
        }
        return new Pair<List<Argument>, List<LexerDataUnit>>(args, newSeq);
    }

    /**
     * <p>
     * Finds all IncompleteOperationLexeme in the sequence, tries to find appropriate function with
     * initializer.
     * </p>
     * <p>
     * Conditions for the operations that should be met:</br>
     *  1. infix operation must be rounded with 2 valid arguments;</br>
     *  2. prefix operation must have right argument and have left parenthesis ornothing to the left;</br>
     *  3. postfix operation must have left argument and have right parenthesis or nothing to the right;
     * </p>
     * @param seq
     *        the working sequence of lexemes.
     * @param initializer
     *        the parser initializer
     * @return the working sequence
     * @throws BadExpressionException
     *         if the operation for some position wasn't resolved.
     */
    private static List<LexerDataUnit>
            defineOperations(List<LexerDataUnit> seq, ExpressionParser initializer)
                    throws BadExpressionException
    {
        boolean finished;
        do {
            finished = true;
            boolean changed = false;
            IncompleteOperationLexeme current = null;
            for (int i = 0; i < seq.size(); i++) {
                if (seq.get(i) instanceof IncompleteOperationLexeme) {
                    finished = false;
                    current  = (IncompleteOperationLexeme) seq
                            .get(i);
                    final String data = ((IncompleteOperationLexeme) current).getData();
                    final LexerDataUnit previous = i != 0 ? seq.get(i - 1) : null;
                    final LexerDataUnit next = i != seq.size() - 1 ? seq.get(i + 1) : null;
                    final boolean hasRightOperand = next instanceof Lexeme 
                            && isRightOperand((Lexeme) next);
                    final boolean hasLeftOperand = previous instanceof Lexeme 
                            && isLeftOperand((Lexeme) previous);
                    //infix branch
                    if (hasLeftOperand && hasRightOperand) {
                        final InfixOperation op = initializer.getInfixOperation(data);
                        if (op != null) {
                            seq.set(i, new Lexeme(current.getStart(), current.getEnd(), op));
                            changed = true;
                        }
                        else {
                            throw new BadExpressionException(current,
                                    "There is no infix function with this name: " + data);
                        }
                    }
                    //prefix branch
                    else if (hasRightOperand && isValidLeftSideForPrefixOperation(previous)) {
                        final PrefixOperation op = initializer.getPrefixOperation(data);
                        if (op != null) {
                            seq.set(i, new Lexeme(current.getStart(), current.getEnd(), op));
                            changed = true;
                        }
                    }
                    //postfix branch
                    else if (hasLeftOperand && isValidRightSideForPostfixOperation(next)) {
                        final PostfixOperation op = initializer.getPostfixOperation(data);
                        if (op != null) {
                            seq.set(i, new Lexeme(current.getStart(), current.getEnd(), op));
                            changed = true;
                        }
                    }
                }
            }
            if (changed == false && finished == false) {
                throw new BadExpressionException(current,
                        "Failed to resolve operations during lexing, breaking infinite cycle");
            }
        }
        while (!finished);
        return seq;
    }

    private static boolean isLeftOperand(Lexeme lexeme)
    {
        final Token token = lexeme.getToken();
        return token instanceof Constant || token == RIGHT_PARENTHESIS
                || token instanceof Argument || token instanceof PostfixOperation;
    }

    private static boolean isRightOperand(Lexeme lexeme)
    {
        final Token token = lexeme.getToken();
        return token instanceof Constant || token == LEFT_PARENTHESIS
                || token instanceof Argument || token instanceof Function
                || token instanceof PrefixOperation;
    }

    private static boolean isValidLeftSideForPrefixOperation(LexerDataUnit unit)
    {
        return unit == null || unit instanceof IncompleteOperationLexeme
                || ((Lexeme) unit).getToken() == LEFT_PARENTHESIS
                || ((Lexeme) unit).getToken() == COMMA;
    }

    private static boolean isValidRightSideForPostfixOperation(LexerDataUnit unit)
    {
        return unit == null || unit instanceof IncompleteOperationLexeme
                || ((Lexeme) unit).getToken() == RIGHT_PARENTHESIS
                || ((Lexeme) unit).getToken() == COMMA;
    }

}
