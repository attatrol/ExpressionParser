# ExpressionParser
Parametrized expression parser written for being used as a module in a larger applications for studying purposes.

1. It processes a string like "cos(x)*3+2*sin(y)/5" or "max(a*a,b*b,c*c) - 12*a*b*c"into an Expression class object, which can calculate value of the expression for any parameters x and y, parametres can be Objects of any type.
2. It finds errors in an input and throws an exception which often contains exact coordinates of the error;
3. It is parametrized with 4 developer defined sets of mathematical actions: functions and infix, prefix and postfix operations;
4. It is almost completely covered with JUnit tests.

User instructions:

1) Use InfixOperation, PrefixOperation, PostfixOperation and Function abstract classes for implementation of your own actions:

	1.1. zero argument functions are allowed;
	1.2. priority of any infix operation must be lower than 1000;
	1.3. order of execution: functions first, then prefix operations, then postfix ones, then infix ones;
2) Create four maps: <String, InfixOperation>, <String, PostfixOperation>, <String, PrefixOperation>, <String, Function>, where values are your newly created actions and keys are their string mappings (designations, names):

	2.1. be careful with naming any operation with symbols used to name functions and variables, parser will parse operation designations first;
	2.2. never use digits in operation designations (not to use digits in designations is a good advise);
	2.3. point, comma and parentesis are reserved symbols.

3) Use the maps to initialize ExpressionParser instance, then use its method parse. ExampleExpressionParserFactory (part of test fixture) may be used as a template.
