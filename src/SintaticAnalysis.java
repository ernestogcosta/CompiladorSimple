import java.util.ArrayList;

/**
 * Valores de tipo:
 * 10 = nova linha
 * 3  = fim de texto
 * 11 = operador aritmetico de atribuicao (=)
 * 21 = operador aritmetico de adicao
 * 22 = operador aritmetico de subtracao
 * 23 = operador aritmetico de divisao inteira
 * 24 = operador aritmetico de multiplicacao
 * 31 = operador relacional igual (==)
 * 32 = operador relacional diferente (!=)
 * 33 = operador relacional maior (>)
 * 34 = operador relacional menor (<)
 * 35 = operador relacional maior ou igual (>=)
 * 36 = operador relacional menor ou igual (<=)
 * 41 = identificador
 * 51 = constante numerica inteira
 * 61 = palavra reservada rem
 * 62 = paralvra reservada input
 * 63 = palavra reservada let
 * 64 = palavra reservada print
 * 65 = palavra reservada goto
 * 66 = palavra reservada if
 * 67 = palavra reservada end
 * 99 = token desconhecido
 */

public class SintaticAnalysis {
    private LexicalAnalysis lexical;
    private int atual, anterior;
    private boolean erro = false;
    /**
     * flagOperacao serve para saber se está sendo feita mais operações que o permitido na linguagem, por exemplo
     * j = a + b + c, que não é permitido, quando uma operação for feita, como o j = a + b, quando o atual for
     * adicao a flagOperacao vira positivo, assim, se chegar novamente em qualquer operacao e a flag for verdadeira,
     * então é um erro.
     * Portanto, no começo de cada operação, caso flagOperacao == true, isso é um erro.
     * ao fim de cada operação, a flag se torna verdadeira
     *
     * flagPalavra é similar, quando uma palavra reservada é analisada, a flagPalavra vira true, e se tiver qualquer
     * coisa após a variável, como por exemplo input j + 1, qualquer coisa após o j é erro, logo, se chegar na variável
     * com flagPalavra == true, então é um erro. Isso não se aplica a alguns casos, somente para input, let, print e goto
     * sendo que goto não é com uma variável e sim com uma constante, porém, a mesma lógica.
     * Portanto, no começo de cada case, exceto identificar e constante, caso flagPalavra seja verdadeiro, isso é erro
     * ao fim de cada case de palavra reservada a flag se torna verdadeira
     *
     * toda vez que começar uma nova linha as flags voltam a ser false
     *
     * flagAtribuicao é a mesma ideia porém somente para atribuição
     */
    private boolean flagOperacao = false, flagAtribuicao = false, flagPalavra = false;
    /**
     * Vou olhar para o token que era antes e comparar com o atual usando as listas
     * O atual está presente na lista do anterior? ou seja
     * o atual é um dos valores que pode acontecer depois do anterior
     * Para isso, entao, tem uma lista para cada valor
    */
    private ArrayList<Integer> aceitos10NovaLinha = new ArrayList<>();
    private ArrayList<Integer> aceitos11Atribuicao = new ArrayList<>();
    private ArrayList<Integer> aceitos21Adicao = new ArrayList<>();
    private ArrayList<Integer> aceitos22Subtracao = new ArrayList<>();
    private ArrayList<Integer> aceitos23Divisao = new ArrayList<>();
    private ArrayList<Integer> aceitos24Multiplicacao = new ArrayList<>();
    private ArrayList<Integer> aceitos31Igual = new ArrayList<>();
    private ArrayList<Integer> aceitos32Diferente = new ArrayList<>();
    private ArrayList<Integer> aceitos33Maior = new ArrayList<>();
    private ArrayList<Integer> aceitos34Menor = new ArrayList<>();
    private ArrayList<Integer> aceitos35MaiorIgual = new ArrayList<>();
    private ArrayList<Integer> aceitos36MenorIgual = new ArrayList<>();
    private ArrayList<Integer> aceitos41Identificador = new ArrayList<>();
    private ArrayList<Integer> aceitos51Constante = new ArrayList<>();
    private ArrayList<Integer> aceitos61Rem = new ArrayList<>();
    private ArrayList<Integer> aceitos62Input = new ArrayList<>();
    private ArrayList<Integer> aceitos63Let = new ArrayList<>();
    private ArrayList<Integer> aceitos64Print = new ArrayList<>();
    private ArrayList<Integer> aceitos65Goto = new ArrayList<>();
    private ArrayList<Integer> aceitos66If = new ArrayList<>();
    private ArrayList<Integer> aceitos67End = new ArrayList<>();

    public SintaticAnalysis(LexicalAnalysis lexical) {
        this.lexical = lexical;
        this.anterior = 10;
        this.aceitos10NovaLinha.add(51); //depois da nova linha pode uma constante
        this.aceitos11Atribuicao.add(41); // depois da atribuicao pode um identificador
        this.aceitos11Atribuicao.add(51); // uma constante
        this.aceitos11Atribuicao.add(22); // e sinal negativo
        this.aceitos21Adicao.add(51); // adicao: constante
        this.aceitos21Adicao.add(41); // adicao: identificador
        this.aceitos22Subtracao.add(51); // subtracao: constante e identificador
        this.aceitos22Subtracao.add(41);
        this.aceitos23Divisao.add(51); // divisao: constante e identificador
        this.aceitos23Divisao.add(41);
        this.aceitos24Multiplicacao.add(51); // multiplicacao: constante e identificador
        this.aceitos24Multiplicacao.add(41);
        this.aceitos31Igual.add(51); // igual: constante e identificador
        this.aceitos31Igual.add(41);
        this.aceitos32Diferente.add(51); // diferente: constante e identificador
        this.aceitos32Diferente.add(41);
        this.aceitos33Maior.add(51); // maior: constante e identificador
        this.aceitos33Maior.add(41);
        this.aceitos34Menor.add(51); // menor: constante e identificador
        this.aceitos34Menor.add(41);
        this.aceitos35MaiorIgual.add(51); // maior ou igual: constante e identificador
        this.aceitos35MaiorIgual.add(41);
        this.aceitos36MenorIgual.add(51); // mejnor ou igual: constante e identificador
        this.aceitos36MenorIgual.add(41);
        this.aceitos41Identificador.add(10); // identificador: nova linha, maior, menor, maior igual, menor igual, igual, atribuicao, diferente, adicao, subtracao, divisao, multiplicacao, goto
        this.aceitos41Identificador.add(11);
        this.aceitos41Identificador.add(21);
        this.aceitos41Identificador.add(22);
        this.aceitos41Identificador.add(23);
        this.aceitos41Identificador.add(24);
        this.aceitos41Identificador.add(31);
        this.aceitos41Identificador.add(32);
        this.aceitos41Identificador.add(33);
        this.aceitos41Identificador.add(34);
        this.aceitos41Identificador.add(35);
        this.aceitos41Identificador.add(36);
        this.aceitos41Identificador.add(65);
        this.aceitos51Constante.add(10); // constante: nova linha
        this.aceitos51Constante.add(11); // atribuicao
        this.aceitos51Constante.add(21); // adicao
        this.aceitos51Constante.add(22); //subtracao
        this.aceitos51Constante.add(23); // divisao
        this.aceitos51Constante.add(24); // multiplicacao
        this.aceitos51Constante.add(31); // igual
        this.aceitos51Constante.add(32); // diferente
        this.aceitos51Constante.add(33); // maior
        this.aceitos51Constante.add(34); // menor
        this.aceitos51Constante.add(35); // maior ou igual
        this.aceitos51Constante.add(36); // menor ou igual
        this.aceitos51Constante.add(41); // identificador
        this.aceitos51Constante.add(61); // rem
        this.aceitos51Constante.add(62); // input
        this.aceitos51Constante.add(63); // let
        this.aceitos51Constante.add(64); // print
        this.aceitos51Constante.add(65); // goto
        this.aceitos51Constante.add(66); // if
        this.aceitos51Constante.add(67); // end
        this.aceitos61Rem.add(10); // depois do rem nada é considerado e se pula direto pra próxima linha, se não for a próxima linha então é erro
        this.aceitos62Input.add(41); // input: só pode ser um identificador
        this.aceitos63Let.add(41); // let: só pode ser um identificador
        this.aceitos64Print.add(41); // print: só pode ser um identificador
        this.aceitos65Goto.add(51); // goto: só pode ser uma constante
        this.aceitos66If.add(41); // if: após ele só pode ser um identificador
        this.aceitos67End.add(3); // end: so pode ser 3, se nao for end of file esta errado
    }

    public void fazerAnaliseSintatica(){
        for(Token item: this.lexical.getTokens()){
            this.atual = item.getType();

            if(erro){
                System.out.println("TERMINANDO ANÁLISE DEVIDO A ERRO SINTÁTICO.");
                break;
            }

            switch(anterior){
                case 10:
                    flagOperacao = false;
                    flagPalavra = false;
                    flagAtribuicao = false;
                    if(!this.aceitos10NovaLinha.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    break;
                case 11:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(flagAtribuicao){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos11Atribuicao.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagAtribuicao = true;
                    break;
                case 21:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(flagOperacao){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos21Adicao.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagOperacao = true;
                    break;
                case 22:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(flagOperacao){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos22Subtracao.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagOperacao = true;
                    break;
                case 23:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(flagOperacao){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos23Divisao.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagOperacao = true;
                    break;
                case 24:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(flagOperacao){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos24Multiplicacao.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagOperacao = true;
                    break;
                case 31:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(flagOperacao){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos31Igual.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagOperacao = true;
                    break;
                case 32:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(flagOperacao){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos32Diferente.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagOperacao = true;
                    break;
                case 33:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(flagOperacao){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos33Maior.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagOperacao = true;
                    break;
                case 34:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(flagOperacao){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos34Menor.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagOperacao = true;
                    break;
                case 35:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(flagOperacao){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos35MaiorIgual.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagOperacao = true;
                    break;
                case 36:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(flagOperacao){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos36MenorIgual.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagOperacao = true;
                    break;
                case 41:
                    if(!this.aceitos41Identificador.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    break;
                case 51:
                    if(!this.aceitos51Constante.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    break;
                case 61:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos61Rem.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    break;
                case 62:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos62Input.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagPalavra = true;
                    break;
                case 63:
                    if(!this.aceitos63Let.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    break;
                case 64:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos64Print.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagPalavra = true;
                    break;
                case 65:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos65Goto.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    flagPalavra = true;
                    break;
                case 66:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos66If.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    this.anterior = this.atual;
                    break;
                case 67:
                    if(flagPalavra){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        erro = true;
                    }
                    if(!this.aceitos67End.contains(atual)){
                        System.out.printf("Erro na linha %d coluna %d\n", item.getLine(), item.getColumn());
                        System.out.println("Após END não há END OF FILE.");
                        erro = true;
                    }else{
                        System.out.println("END OF FILE!");
                    }
                    break;
                case 99:
                    System.out.println("Token desconhecido: " + item);
                    break;
            }
        }

        if(!erro){
            System.out.println("Nenhum erro sintático no código.");
        }
    }

}
