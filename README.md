
# Recursive Descent Parser in Java

This repository implements a recursive descent parser in Java based on a provided grammar. 

**Grammar**

The parser is designed to handle the following grammar productions:
```
<program> ::= <lib-decl> <declarations> (<function-decl>)* <block> <exit>

<lib-decl> ::= "#include" "<" <file-name> ">" ";" <lib-decl> | ε

<declarations> ::= <const-decl> <var-decl>

<const-decl> ::= "const" <data-type> <const-name> "=" <value> ";" <const-decl> | ε

<var-decl> ::= "var" <data-type> <name-list> ";" <var-decl> | ε

<name-list> ::= <var-name> <more-names>

<more-names> ::= "," <name-list> | ε

<data-type> ::= "int" | "float" | "char"

<function-decl> ::= <function-heading> <declarations> <block> ";"

<function-heading> ::= "function" <function-name> ";"

<block> ::= "{" <stmt-list> "}"

<stmt-list> ::= <statement> ";" <stmt-list> | ε

<statement> ::= <ass-stmt> | <inout-stmt> | <if-stmt> | <while-stmt> | <block> | <repeat-stmt> | <function-call-stmt>

<ass-stmt> ::= <var-name> ":=" <exp>

<exp> ::= <term> <exp-prime>

<exp-prime> ::= <add-oper> <term> <exp-prime> | ε

<term> ::= <factor> <term-prime>

<term-prime> ::= <mul-oper> <factor> <term-prime> | ε

<factor> ::= "(" <exp> ")" | <var-name> | <const-name> | <value>

<add-oper> ::= "+" | "-"

<mul-oper> ::= "*" | "/" | "mod" | "div"

<inout-stmt> ::= "cin" ">>" <var-name> | "cout" "<<" <name-value>

<if-stmt> ::= "if" "(" <condition> ")" <statement> <else-part>

<else-part> ::= "else" <statement> | ε

<while-stmt> ::= "while" "(" <condition> ")" "{" <stmt-list> "}"

<repeat-stmt> ::= "repeat" <stmt-list> "until" <condition>

<condition> ::= <name-value> <relational-oper> <name-value>

<name-value> ::= <var-name> | <const-name> | <value>

<relational-oper> ::= "=" | "!=" | "<" | "<=" | ">" | ">="

<name> ::= <letter> (<letter> | <digit>)*

<value> ::= <integer-value> | <real-value>

<integer-value> ::= <digit>+

<real-value> ::= <digit>+ "." <digit>+

<function-call-stmt> ::= "call" <function-name>
```

**Usage**

1.  **Create an input file:** Save the code you want to parse to a file named `code.txt`.

