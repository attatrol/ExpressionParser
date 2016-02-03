package attatrol.exparser.lexer;

import java.util.List;

import attatrol.exparser.tokens.Argument;

/**
 * POJO, result of the lexing,
 * unites a list of the formula arguments
 * and a complete list of lexemes.
 * 
 * @author atta_troll
 *
 */
public class LexerOutput {
  private List<Lexeme> lexemes;
  private List<Argument> arguments;
  
  public LexerOutput(List<Lexeme> lexemes, List<Argument> arguments) {
    super();
    this.lexemes = lexemes;
    this.arguments = arguments;
  }

  public List<Lexeme> getLexemes() {
    return lexemes;
  }

  public List<Argument> getArguments() {
    return arguments;
  }
  

}
