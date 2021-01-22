// Define a grammar called Hello
grammar Lexer;

text : DOLLAR texttodollar text
    | C UNDERDASH WORD POWER WORD text
    | strange_operation WORD text
    | strange_operation FIGUREOPEN texttofigureclose text
    | normal_operation text
    | FRAC FIGUREOPEN texttofigureclose FIGUREOPEN texttofigureclose text
    | WORD text
    | EOF;

texttodollar : strange_operation WORD texttodollar
    | strange_operation FIGUREOPEN texttofigureclose texttodollar
    | normal_operation texttodollar
    | FRAC FIGUREOPEN texttofigureclose FIGUREOPEN texttofigureclose texttodollar
    | WORD texttodollar
    | DOLLAR;

texttofigureclose : strange_operation WORD texttofigureclose
    | strange_operation FIGUREOPEN texttofigureclose texttofigureclose
    | normal_operation texttofigureclose
    | FRAC FIGUREOPEN texttofigureclose FIGUREOPEN texttofigureclose texttofigureclose
    | WORD texttofigureclose
    | FIGURECLOSE;

normal_operation : EQUALITY | PLUS | MINUS;
strange_operation : POWER | UNDERDASH;

/*
 * Lexer Rules
 */
C  : '\\C';
WORD : [a-zA-Z0-9][a-zA-Z0-9]*;
FRAC : '\\frac';

WHITESPACE : [ \t\r\n]+ -> skip;

DOLLAR : '$';
EQUALITY : '=';
POWER : '^';
UNDERDASH : '_';
FIGUREOPEN : '{';
FIGURECLOSE : '}';
PLUS  : '+';
MINUS  : '-';