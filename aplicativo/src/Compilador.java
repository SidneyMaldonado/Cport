import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Compilador {

    public String nomeArquivo;
    public List<String> codigoFonte;
    public HashMap<String, Integer> tabelaSimbolos;
    public int numerolinha = 0;
    public List<String> byteCode; // codigo que sera gerado pelo compilador

    public Compilador( List<String> fonte ){
        this.codigoFonte = fonte;
        this.byteCode = new ArrayList<>();

    }

    public Boolean compilar(){

        Boolean erro = false;
        this.numerolinha = 0;

        // passar por todas as linhas do codigo fonte
        for(String linha:  this.codigoFonte){

            erro = compilarLinha( linha.trim() );

            if (erro){
                System.out.println("Erro ao compilar o programa: " + this.numerolinha + " => " + linha);
            }
            numerolinha++;
        }
        if (!erro){
            gravarByteCode();
        }
        return !erro;
    }

    private Boolean compilarLinha(String linha) {
        Boolean erro = false;

        if (linha.startsWith("inicio")){
            erro = processarInicio(linha);
        }
        if (linha.equals("fim")){
            erro = processarFim(linha);
        }

        if (linha.startsWith("declare")){
            erro = processarDeclare(linha);
        }
        if (linha.startsWith("escreva") ){
            erro = processarEscreva(linha);
        }
        if (linha.startsWith("imprimir") ){
            linha = linha.replace("imprimir","escreva");
            erro = processarEscreva(linha);
        }
        if (linha.equals("novalinha")){
            this.byteCode.add("printf(\"\\n\");");
        }

        if (linha.startsWith("conte")){
            erro = processarConte(linha);
        }

        if (linha.equals("fimconte")){
            this.byteCode.add("}");

        }

        if (linha.startsWith("se")){
            erro = processarSe(linha);
        }

        if (linha.equals("fimse")){
            this.byteCode.add("}");
        }

        if (linha.startsWith("leia")){
            erro = processarLeia(linha);
        }

        if (linha.startsWith("$")){
            erro = processarExpressao(linha);
        }

        if (linha.equals("pausa")){
            erro = processarPausa();
        }

        return erro;
    }

    private Boolean processarInicio(String linha) {
        Boolean erro = false;
        this.byteCode.add("#include  <iostream>");
        this.byteCode.add("int main(int argc, char** argv) {");

        String tokens[] = linha.split(" ");
        this.nomeArquivo = tokens[1].replaceAll("\"", "");
        this.nomeArquivo += ".cpp";

        return erro;
    }

    public Boolean processarFim(String linha){
        Boolean erro = false;
        this.byteCode.add("return 0;");
        this.byteCode.add("}");
        return erro;
    }

    public Boolean gravarByteCode(){

        Arquivo byteCode = new Arquivo(this.nomeArquivo) ;
        byteCode.gravarArquivo( this.byteCode );

        return true;
    }

    public Boolean processarDeclare( String linha ){

        Boolean erro = false;
        // trocar declare por int
        String resultado  = linha.replace("declare", "int");
        // remover o $
        resultado = resultado.replaceAll("$","");
        // adicionar o ; no final
        resultado += ";";
        this.byteCode.add(resultado);

        return erro;
    }

    public Boolean processarEscreva(String linha){

        Boolean erro = false;
        // remover o comando escreva
        String resultado = linha.replace("escreva","");

        // adicionar o comando printf
        if (linha.contains("$")){
            resultado = resultado.replaceAll("$","");
            resultado = "printf( \"%i\", " + resultado;

        } else {
            resultado = "printf(" + resultado;
        }

        // adicionar o ); no fim
        resultado += ");";

        this.byteCode.add(resultado);

        return erro;
    }

    public Boolean processarConte( String linha ){

        Boolean erro = false;
        String tokens[] = linha.split(" ");

        String inicio = tokens[4];
        String fim = tokens[6];
        String variavel = tokens[2];
        String resultado = "for( " + variavel +"="+inicio+";" + variavel +" < "+fim+";"+variavel +"++){";
        this.byteCode.add(resultado);
        return erro;
    }

    private Boolean processarSe(String linha) {
        Boolean erro = false;
        String tokens[] = linha.split(" ");
        String variavel = tokens[1];
        String operador= tokens[2];
        String valor = tokens[3];
        String resultado = "if ( " + variavel+operador+valor +"){";
        this.byteCode.add(resultado);
        return erro;
    }

    private Boolean processarLeia(String linha){

        Boolean erro = false;
        String variavel = linha.replace("leia ","");
        String resultado = "scanf( \"%i\", &"+variavel+");";
        this.byteCode.add(resultado);
        return erro;
    }
    private Boolean processarExpressao(String linha){
        Boolean erro = false;
        String tokens[] = linha.split(" ");
        String destino = tokens[0];
        String var1 = tokens[2];
        String var2 = tokens[4];
        String operador = tokens[3];
        String resultado = destino + " = "  + var1 + operador+var2+";";
        this.byteCode.add(resultado);
        return erro;
    }

    private Boolean processarPausa(){

        this.byteCode.add("printf(\"\\n\");");
        this.byteCode.add("system(\"pause\");");
        return false;

    }

}
