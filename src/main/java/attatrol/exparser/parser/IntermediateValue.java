package attatrol.exparser.parser;

import attatrol.exparser.lexer.Lexeme;

/**
 * Stab, replaces processed lexemes and has a meaning of
 * some processed intermediate value, carries its own index.
 * Used by ParserProcessor exclusively.
 * 
 * @author atta_troll
 *
 */
class IntermediateValue extends Lexeme
{
    private int index;

    public IntermediateValue(int index)
    {
        super(0, 0, null);
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }
    

}
