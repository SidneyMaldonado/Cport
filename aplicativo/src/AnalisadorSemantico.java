import java.util.ArrayList;
import java.util.List;

public class AnalisadorSemantico {

    public List<String> tabelaSimbolos;
    public List<String> codigoFonte;

    public AnalisadorSemantico( List<String> fonte){
        this.codigoFonte = fonte;
        this.tabelaSimbolos = new ArrayList<>();
    }

    public Boolean analisar(){
        System.out.println("-------------------------");
        System.out.println("ANALISE SEMANTICA:");
        System.out.println("-------------------------");

        Boolean erro = false;
        int numerolinha=0;
        for(String linha: codigoFonte){

            Boolean resultado = analisarLinha( linha.trim(), numerolinha );
            if (!resultado){ // se deu erro
                if (!erro) {
                    erro = true;
                }
                System.out.println("Erro na linha:" + numerolinha + " " + linha + " -> variavel não declarada");

            }
            numerolinha++;
        }
        System.out.println("-------------------------");
        return !erro;
    }

    public boolean analisarLinha(String linha, int nl){

        String tokens[] = linha.split(" "); // separar os tokens
        String nomeVariavel="";

        // validar todas as linhas que tem tratamento de variaveis
        if (linha.startsWith("declare")){ // se for declare adiciona na tabela de simbolos
            nomeVariavel = tokens[1];
            adicionarTabelaSimbolos(nomeVariavel);
            return true;
        }
        if (linha.startsWith("leia")){
            nomeVariavel = tokens[1];
            return existeVariavel(nomeVariavel);
        }
        if (linha.startsWith("imprimir")){
            nomeVariavel = tokens[1];
            return existeVariavel(nomeVariavel);
        }
        if (linha.startsWith("escreva ")) {
            if (!linha.contains("\"")) {
                nomeVariavel = tokens[1];
                return existeVariavel(nomeVariavel);
            }
        }

        if (tokens.length > 1){
        if (tokens[1].equals("=")){
            return existeVariavel(tokens[0]) && existeVariavel(tokens[2]) && existeVariavel(tokens[4]);
        }}

        if (linha.startsWith("conte")){
            nomeVariavel = tokens[2];
            return existeVariavel(nomeVariavel);
        }

        if (linha.startsWith("se")){
            nomeVariavel = tokens[1];
            return existeVariavel(nomeVariavel);
        }
        return true;
    }

    // adicionar na tabela de simbolo
    // remover da tabela simbolo
    // verficar se existe na tabela de simbolos

    public void adicionarTabelaSimbolos(String variavel){
        tabelaSimbolos.add(variavel);
    }
    public void removerTabelaSimbolos(String variavel){
        tabelaSimbolos.remove(variavel);
    }
    public Boolean existeVariavel( String variavel ){

        return tabelaSimbolos.contains(variavel);
    }

}
