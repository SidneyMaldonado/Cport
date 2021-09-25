import java.util.ArrayList;
import java.util.List;

public class AnalisadorSintatico {


    public List<String> gramatica;
    public List<String> fonte;

    public AnalisadorSintatico( List<String> gramatica, List<String> fonte){
        this.gramatica = gramatica;
        this.fonte = fonte;
    }

    public Boolean analisar(){
        System.out.println("-------------------------");
        System.out.println("ANALISE SINTÁTICA:");
        int numerolinha =0;
        Boolean erros = false;
        for(String linha: this.fonte){

            Boolean resultado = analisar(linha.trim());

            if (!resultado){
                System.out.print("Validando linha: " + numerolinha + "  => ");
                System.out.println("**** Erro de sintaxe na linha: " + numerolinha +" => " + linha);
                erros = true;
            }
            numerolinha++;
        }
        return !erros;
    }

    public boolean analisar(String linha){
        for(String regex: this.gramatica){
            if (linha.matches(regex)){ // achou uma gramatica que a autoriza
                return true;
            }
        }
        System.out.println("Erro na linha: " + linha);
        return false;
    }






}
