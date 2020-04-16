import javax.sound.midi.Soundbank;
import java.util.ArrayList;

public class SemanticAnalysis {
    private int atual, anterior;
    private LexicalAnalysis lexical;
    private ArrayList<String> lista = new ArrayList<>();
    private boolean erro = false;

    public SemanticAnalysis(LexicalAnalysis lexical) {
        this.anterior = 0;
        this.lexical = lexical;
    }

    public void fazerAnaliseSemantica(){
        // Pegar a lista de chaves da análise léxica e colocar em uma lista
        for(String key: lexical.getSymbolTable().keySet())
        {
            lista.add(key);
        }

        for(Token item: lexical.getTokens()){
            if(erro){
                System.out.println("TERMINANDO ANÁLISE DEVIDO A ERRO SEMÂNTICO.");
                break;

            }

            // Se o token for da coluna 1, ou seja, primeiro item da linha do código, então faço
            if(item.getColumn() == 1){
                atual = Integer.parseInt(lista.get(item.getAddress()));

                if(atual < anterior){
                    System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                    erro = true;
                }else{
                    anterior = atual;
                }
            }
        }

        if(!erro){
            System.out.println("Nenhum erro semântico no código.");
        }
    }
}
