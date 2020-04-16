import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Classe responsavel pelo compilador da linguagem de programacao
 * SIMPLE 15.01
 */
public class Compiler
{
    /**
     * Metodo principal da linguagem de programacao Java
     *
     * @param args argumentos da linha de comando (nao utilizado)
     */
    public static void main(String[] args)
    {
        File file = new File("C:\\Users\\Ernesto\\Documents\\codigoSimple.txt");
        BufferedReader source = null;

        try
        {
            source = new BufferedReader(new FileReader(file));
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }

        System.out.println("Início da análise léxica");

        LexicalAnalysis lexical = new LexicalAnalysis();

        if(!lexical.parser(source))
        {
            for(String key: lexical.getSymbolTable().keySet())
            {
                System.out.println(lexical.getSymbolTable().get(key) + " : " + key);
            }

            for(Token token: lexical.getTokens())
            {
                System.out.println(token);
            }
        }

        System.out.println("Fim da análise léxica");
        System.out.println("---------------------------------------------------------------------------");

        System.out.println("Início da análise sintática");

        SintaticAnalysis sintatic = new SintaticAnalysis(lexical);
        sintatic.fazerAnaliseSintatica();

        System.out.println("Fim da análise sintática");
        System.out.println("---------------------------------------------------------------------------");

        System.out.println("Início da análise semântica");

        SemanticAnalysis semantic = new SemanticAnalysis(lexical);
        semantic.fazerAnaliseSemantica();

        System.out.println("Fim da análise semântica");
        System.out.println("---------------------------------------------------------------------------");
    }
}