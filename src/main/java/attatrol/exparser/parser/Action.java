package attatrol.exparser.parser;

import attatrol.exparser.tokens.Token;
/**
 * POJO, unites argument indexes and 
 * an executive token. Used by Expression exclusively.
 * 
 * @author atta_troll
 *
 */
public class Action
{
    private int[] argumentList;
    private Token action;
    
    public Action(int[] argumentList, Token action)
    {
        this.argumentList = argumentList;
        this.action = action;
    }

    public int[] getArgumentList()
    {
        return argumentList;
    }

    public Token getAction()
    {
        return action;
    }
}
