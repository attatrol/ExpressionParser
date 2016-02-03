package attatrol.exparser;

import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import attatrol.exparser.lexer.Lexeme;
import attatrol.exparser.lexer.Lexer;
import attatrol.exparser.lexer.LexerOutput;
import attatrol.exparser.parser.Expression;
import attatrol.exparser.parser.ParserProcessor;
import attatrol.exparser.tokens.Comma;
import attatrol.exparser.tokens.Function;
import attatrol.exparser.tokens.InfixOperation;
import attatrol.exparser.tokens.LeftParenthesis;
import attatrol.exparser.tokens.PostfixOperation;
import attatrol.exparser.tokens.PrefixOperation;
import attatrol.exparser.tokens.RightParenthesis;
import attatrol.exparser.verifier.ExpressionIntegrityVerifier;
import attatrol.exparser.verifier.FunctionSyntaxVerifier;
import attatrol.exparser.verifier.MeaningfulnessVerifier;
import attatrol.exparser.verifier.ParenthesisLevelVerifier;

public class ExpressionParser {
  
    /**
     * Hard-coded designations of common symbols
     * TODO decide in future if they should be tunable
     */
    public static final char LEFT_PARENTHESIS_DSGN = '(';
    public static final char RIGHT_PARENTHESIS_DSGN = ')';
    public static final char COMMA_DSGN = ',';
    
    /**
     * Parentheses and comma are most hard-coded archetypes of the parser.
     * It is presumed that parser uses single object for any of them.
     */
    public static final LeftParenthesis LEFT_PARENTHESIS = new LeftParenthesis();
    public static final RightParenthesis RIGHT_PARENTHESIS = new RightParenthesis();
    public static final Comma COMMA = new Comma();

  private Map<String, Function> functionLookup;
  private Map<String, InfixOperation> infixOperationLookup;
  private Map<String, PrefixOperation> prefixOperationLookup;
  private Map<String, PostfixOperation> postfixOperationLookup;
    
  @SuppressWarnings("unused")
    private ExpressionParser() {
      
  }

  public ExpressionParser(Map<String, Function> functionLookup,
        Map<String, InfixOperation> infixOperationLookup,
        Map<String, PrefixOperation> prefixOperationLookup,
        Map<String, PostfixOperation> postfixOperationLookup)
{
    this.functionLookup = functionLookup;
    this.infixOperationLookup = infixOperationLookup;
    this.prefixOperationLookup = prefixOperationLookup;
    this.postfixOperationLookup = postfixOperationLookup;
}


public Function getFunction(String arg) {
    return functionLookup.get(arg);
  }

  public InfixOperation getInfixOperation(String arg) {
    return infixOperationLookup.get(arg);
  }

  public PrefixOperation getPrefixOperation(String arg) {
    return prefixOperationLookup.get(arg);
  }

  public PostfixOperation getPostfixOperation(String arg) {
    return postfixOperationLookup.get(arg);
  }

  public Set<String> getOperationNames() {
    Set<String> result = new HashSet<>(infixOperationLookup.keySet());
    result.addAll(prefixOperationLookup.keySet());
    result.addAll(postfixOperationLookup.keySet());
    return result;
  }
  
  public Set<String> getFunctionNames() {
    return functionLookup.keySet();
  }

  public Expression parse(String input) 
          throws BadExpressionException {
      final LexerOutput lexerOutput = Lexer.lexing(input, this);
      final List<Lexeme> lexemes = lexerOutput.getLexemes();
      
      MeaningfulnessVerifier.verify(lexemes);
      ParenthesisLevelVerifier.verify(lexemes);
      FunctionSyntaxVerifier.verify(lexemes);
      ExpressionIntegrityVerifier.verify(lexemes);
      
      return ParserProcessor.processParsing(lexerOutput);
  }
  

}
