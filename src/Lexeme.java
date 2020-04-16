/**
 * Classe responsavel pela representacao de um lexema
 */
public class Lexeme
{
    /**
     * Termo lido do codigo-fonte
     */
    private StringBuilder term;

    /**
     * Tipo/classe do lexema
     */
    private int type;

    /**
     * Numero da linha no codigo-fonte
     */
    private int line;

    /**
     * Numero da coluna no codigo-fonte
     */
    private int column;

    /**
     * Construtor para inicializar o lexema
     *
     * @param character caractere lido do codigo-fonte
     * @param type tipo/classe do lexema
     * @param line numero da linha no codigo-fonte
     * @param column numero da coluna no codigo-fonte
     */
    public Lexeme(char character, int type, int line, int column)
    {
        term = new StringBuilder().append(character);

        this.type = type;
        this.line = line;
        this.column = column;
    }

    /**
     * Adicionar um caractere ao termo lido do codigo-fonte
     *
     * @param character caractere lido do codigo-fonte
     * @param type tipo/classe do lexema
     */
    public void append(char character, int type)
    {
        term.append(character);

        this.type = type;
    }

    /**
     * Retornar o termo lido do codigo-fonte
     *
     * @return termo lido do codigo-fonte
     */
    public String getTerm()
    {
        return term.toString();
    }

    /**
     * Retornar o tipo/classe do lexema
     *
     * @return tipo/classe do lexema
     */
    public int getType()
    {
        return type;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "'" + getTerm() + "' (" + line + ", " + column + ")";
    }

    /**
     * Retornar o token correspondente ao lexema - sem tabela de simbolo
     *
     * @return token correspondente ao lexema - sem tabela de simbolo
     */
    public Token toToken()
    {
        return new Token(type, line, column);
    }

    /**
     * Retornar o token correspondente ao lexema - com tabela de simbolo
     *
     * @param address endereco na tabela de simbolos
     * @return token correspondente ao lexema - com tabela de simbolo
     */
    public Token toToken(int address)
    {
        return new Token(type, address, line, column);
    }
}