import java.util.ArrayList;
import java.util.List;

public class AnalisadorLexico {

    public List<String> dicionario;
    public List<String> fonte;
    public List<String> erros;
    public List<String> tokens;
    public List<Integer> tokenLine;

    public AnalisadorLexico( List<String> dicionario, List<String> codigoFonte){
        this.dicionario = dicionario;
        this.fonte = codigoFonte;
        this.erros = new ArrayList();
        this.tokenLine = new ArrayList();
        this.tokens = converterParaToken();
    }

    // cria um vetor com todas as palavras separadas
    public List<String> converterParaToken(){
        List<String> palavras = new ArrayList();

        Integer linhanumero = 0;
        for(String linha: this.fonte){

            linha = linha.trim(); // remove os espacos em branco
            // quebrar em palavras

            String termos[] = linha.split(" ");

            if (linha.contains("\"")){    // se a linha contem aspas
                palavras.add( termos[0]);
                this.tokenLine.add(linhanumero);
                String novalinha = linha.replace(termos[0],"").trim();
                palavras.add(novalinha);
                this.tokenLine.add(linhanumero);

            } else {
                // adiciona no vetor todas as palavras
                for( String t: termos){
                    palavras.add(t);
                    this.tokenLine.add(linhanumero);
                }
            }
            linhanumero++;
        }
        return palavras;
    }

    public boolean analisar(){

        System.out.println("-------------------------");
        System.out.println("ANALISE LEXICA:");

        Integer palavranumero = 0;

        for(String palavra: this.tokens){

            Boolean resultado = validar(palavra);
            if (resultado == false){
                System.out.print("Linha: " + this.tokenLine.get(palavranumero) + ": ");
                System.out.print(palavra + " => validando => ");
                System.out.print(" ****** ERRO ***** ");
                System.out.println(resultado);
            }

            palavranumero++;
        }
        System.out.println("-------------------------");

        Boolean resultado = this.erros.size() == 0;
        return resultado;
    }

    public Boolean validar(String token){

        for(String expressao: this.dicionario){
            if (token.matches(expressao)){
                return true;
            }
        }
        this.erros.add("Erro de validacao na termo: " + token );
        return false;
    }
}
