package attatrol.exparser.parser;

import java.util.ArrayList;
import java.util.List;

import attatrol.exparser.tokens.Function;
import attatrol.exparser.tokens.InfixOperation;
import attatrol.exparser.tokens.PostfixOperation;
import attatrol.exparser.tokens.PrefixOperation;
import attatrol.exparser.tokens.Token;

public final class Expression
{
    private String[] argumentNames;
    private double[] constants;
    private Action[] actions;
    private int arity;
    private int constantsNumber;
    


    public Expression(String[] argumentNames, double[] constants, Action[] actions, int arity,
            int constantsNumber)
    {
        this.argumentNames = argumentNames;
        this.constants = constants;
        this.actions = actions;
        this.arity = arity;
        this.constantsNumber = constantsNumber;
    }

    public double calculate(double[] arguments)
            throws IllegalArgumentException
    {
        if (arguments.length != arity) {
            throw new IllegalArgumentException("Incorrect arity of argument array");
        }
        else if (actions.length == 0) {
            if (arity == 0) {
                return constants[0];
            }
            else {
                return arguments[0];
            }
        }
        else {
            List<Double> actionResults = new ArrayList<>(actions.length);
            for (Action action : actions) {
                final Token archetype = action.getAction();
                final int[] args = action.getArgumentList();
                if (archetype instanceof InfixOperation) {
                    actionResults.add(((InfixOperation) archetype).calculate(
                            resolveArgument(arguments, actionResults, args[0]),
                            resolveArgument(arguments, actionResults, args[1])));
                }
                else if (archetype instanceof PrefixOperation) {
                    actionResults.add(((PrefixOperation) archetype).calculate(
                            resolveArgument(arguments, actionResults, args[0])));
                }
                else if (archetype instanceof PostfixOperation) {
                    actionResults.add(((PostfixOperation) archetype).calculate(
                            resolveArgument(arguments, actionResults, args[0])));
                }
                else {
                    final Function func = (Function) archetype;
                    double[] cortege = new double[func.getArity()];
                    for (int i=0; i<args.length; i++) {
                        cortege[i] = resolveArgument(arguments, actionResults, args[i]);
                    }
                    actionResults.add(func.calculate(cortege));
                }
            }

            return actionResults.get(actionResults.size() - 1);
        }
    }

    private double resolveArgument(double[] arguments, List<Double> actionResults, int index)
    {
        if (index < arity) {
            return arguments[index];
        }
        index -= arity;
        if (index < constantsNumber) {
            return constants[index];
        }
        index -= constantsNumber;
        return actionResults.get(index);
    }

    public String[] getArgumentNames()
    {
        return argumentNames;
    }

    public int getArity()
    {
        return arity;
    }
    

}
