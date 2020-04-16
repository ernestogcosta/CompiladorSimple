/**
 * Interface responsavel pelas constantes utilizadas na fase de analise
 */
public interface Symbol
{
    /**
     * Delimitador de nova linha
     */
    public int LF = 10;

    /**
     * Delimitador de fim de texto
     */
    public int ETX = 3;

    /**
     * Operador de atribuicao (=)
     */
    public int ASSIGNMENT = 11;

    /**
     * Operador aritmetico de adicao (+)
     */
    public int ADD = 21;

    /**
     * Operador aritmetico de subtracao (-)
     */
    public int SUBTRACT = 22;

    /**
     * Operador aritmetico de divisao inteira (/)
     */
    public int DIVIDE = 23;

    /**
     * Operador aritmetico de multiplicacao (*)
     */
    public int MULTIPLY = 24;

    /**
     * Operador relacional igual a (==)
     */
    public int EQ = 31;

    /**
     * Operador relacional diferente de (!=)
     */
    public int NE = 32;

    /**
     * Operador relacional maior que (>)
     */
    public int GT = 33;

    /**
     * Operador relacional menor que (<)
     */
    public int LT = 34;

    /**
     * Operador relacional maior ou igual a (>=)
     */
    public int GE = 35;

    /**
     * Operador relacional menor ou igual a (<=)
     */
    public int LE = 36;

    /**
     * Identificador
     */
    public int VARIABLE = 41;

    /**
     * Constante numerica inteira
     */
    public int INTEGER = 51;

    /**
     * Palavra reservada rem
     */
    public int REM = 61;

    /**
     * Palavra reservada input
     */
    public int INPUT = 62;

    /**
     * Palavra reservada let
     */
    public int LET = 63;

    /**
     * Palavra reservada print
     */
    public int PRINT = 64;

    /**
     * Palavra reservada goto
     */
    public int GOTO = 65;

    /**
     * Palavra reservada if
     */
    public int IF = 66;

    /**
     * Palavra reservada end
     */
    public int END = 67;

    /**
     * Token nao reconhecido
     */
    public int ERROR = 99;
}