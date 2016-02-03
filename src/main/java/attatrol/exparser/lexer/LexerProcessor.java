package attatrol.exparser.lexer;

import java.util.List;

import attatrol.exparser.ExpressionParser;

/**
 * Describes processors for some lexing stages.
 * 
 * @author atta_troll
 *
 */
interface LexerProcessor {
    List<LexerDataUnit> process(UnprocessedDataUnit unit, ExpressionParser init);
}
