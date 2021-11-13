import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Interpretador {

    public List<String> codigoFonte;
    public HashMap<String, Integer> tabelaSimbolos;
    public int numerolinha = 0;

    public Interpretador(List<String> fonte) {
        this.codigoFonte = fonte;
        tabelaSimbolos = new HashMap<String, Integer>();
    }


    public Interpretador(List<String> fonte, HashMap<String, Integer> tabela) {
        this.codigoFonte = fonte;
        this.tabelaSimbolos = tabela;
    }


    public HashMap<String, Integer> interpretar() {

        Boolean erro = false;
        numerolinha = 0;

        for (numerolinha = 0; numerolinha < codigoFonte.size(); numerolinha++) {

            String linha = codigoFonte.get(numerolinha);
            Boolean resultado = processarLinha(linha.trim());

            if (!resultado) {
                System.out.println("Erro ao processar a linha:" + numerolinha);
                numerolinha++;
            }
        }
        return tabelaSimbolos;
    }

    public Boolean processarLinha(String linha) {
        Boolean resultado = true;

        if (linha.startsWith("declare")) {
            resultado = processarDeclare(linha);
        }

        if (linha.startsWith("leia")) {
            resultado = processarLeia(linha);
        }

        if (linha.startsWith("escreva")) {
            resultado = processarEscreva(linha);
        }

        if (linha.startsWith("imprimir")) {
            linha = linha.replace("imprimir", "escreva");
            resultado = processarEscreva(linha);
        }

        if (linha.startsWith("novalinha")) {
            System.out.println("");
        }

        if (linha.startsWith("conte em")) {
            resultado = processarConte(linha);
        }

        if (linha.split(" ").length == 5) {
            if (linha.split(" ")[1].equals("=")) {
                processarAtribuicao(linha);

            }
        }

        if (linha.startsWith("se")){
            resultado = processarSe(linha);
        }
        if (linha.startsWith("pausa")){
            processarPausa();
        }

        return resultado;
    }

    private void processarPausa() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\nPressione Enter para continuar...");
        String pausa = teclado.nextLine();
    }

    public Boolean processarDeclare(String linha) {

        String tokens[] = linha.split(" ");
        if (tabelaSimbolos.containsKey(tokens[1])) {  //  variavel já existe na tabela de símbolos
            System.out.println("Variavel já existe na tabela de simbolos:" + tokens[1]);
            return false;
        }
        Integer valor = 0;
        tabelaSimbolos.put(tokens[1], valor);
        return true;
    }

    public Boolean processarLeia(String linha) {
        String tokens[] = linha.split(" ");

        if (!tabelaSimbolos.containsKey(tokens[1])) {  //  nao esta na tabela de simbolos
            System.out.println("Variavel nao declarada:" + tokens[1]);
            return false;
        }

        // ler a variavel
        Scanner teclado = new Scanner(System.in);
        Integer value = teclado.nextInt();
        tabelaSimbolos.replace(tokens[1], value);
        return true;
    }


    public Boolean processarEscreva(String linha) {
        String tokens[] = linha.split(" ");
        if (tokens[1].startsWith("\"")) {
            linha = linha.replace("escreva \"", "");
            linha = linha.replaceAll("\"", "");
            System.out.print(linha);
        } else {

            if (!tabelaSimbolos.containsKey(tokens[1])) {  //  nao esta na tabela de simbolos
                System.out.println("Variavel nao declarada:" + tokens[1]);
                return false;
            }

            System.out.print(tabelaSimbolos.get(tokens[1])); // imprimir o valor da variavel
        }
        return true;
    }

    public Boolean processarConte(String linha) {
        // ponto inicial é o numero da linha
        // ponto final é o fimconte

        List<String> blocoCodigo = new ArrayList<>();
        int linhaFinal = this.numerolinha + 1;
        int linhaInicial = this.numerolinha + 1;

        while (!codigoFonte.get(linhaFinal).trim().startsWith("fimconte")) {
            blocoCodigo.add(codigoFonte.get(linhaFinal).trim());
            linhaFinal++;
        }

        String tokens[] = linha.split(" ");

        int inicio = Integer.parseInt(tokens[4]); // inicio
        int fim = Integer.parseInt(tokens[6]); // fim
        String variavel = tokens[2]; // variavel

        for (int n = inicio; n < fim; n++) {
            tabelaSimbolos.replace(variavel, n);
            Interpretador novoInterpretador = new Interpretador(blocoCodigo, this.tabelaSimbolos);
            this.tabelaSimbolos = novoInterpretador.interpretar();
        }

        this.numerolinha = linhaFinal + 1;

        return true;

    }

    public Boolean processarAtribuicao(String linha) {
        String tokens[] = linha.split(" ");

        String destino = tokens[0];
        String operador = tokens[2];
        String operando = tokens[4];
        String operacao = tokens[3];
        Integer valorOperando = Integer.parseInt(this.tabelaSimbolos.get(operando).toString());
        Integer valorOperador = Integer.parseInt(this.tabelaSimbolos.get(operador).toString());

        if (operacao.equals("*")) {
            this.tabelaSimbolos.replace(destino, valorOperador * valorOperando);
        }
        if (operacao.equals("/")) {
            this.tabelaSimbolos.replace(destino, valorOperador * valorOperando);
        }
        if (operacao.equals("+")) {
            this.tabelaSimbolos.replace(destino, valorOperador * valorOperando);
        }
        if (operacao.equals("-")) {
            this.tabelaSimbolos.replace(destino, valorOperador * valorOperando);
        }

        return true;
    }

    public Boolean processarSe( String linha){

        List<String> blocoCodigo = new ArrayList<>();
        int linhaFinal = this.numerolinha + 1;
        int linhaInicial = this.numerolinha + 1;

        while (!codigoFonte.get(linhaFinal).trim().startsWith("fimse")) {
            blocoCodigo.add(codigoFonte.get(linhaFinal).trim());
            linhaFinal++;
        }

        String tokens[] = linha.split(" ");
        Integer valor1 = Integer.parseInt(tabelaSimbolos.get(tokens[1]).toString());
        Integer valor2 = Integer.parseInt(tokens[3]);

        Boolean executar = false;
        if (tokens[2].equals(">")) {
            if ( valor1 > valor2){
                executar = true;
            }
        }

        if (tokens[2].equals("<")) {
            if ( valor1 < valor2){
                executar = true;
            }
        }
        if (tokens[2].equals("=")) {
            if ( valor1 == valor2){
                executar = true;
            }
        }
        if (tokens[2].equals("!")) {
            if ( valor1 != valor2){
                executar = true;
            }
        }

        if (executar) {
            Interpretador novoInterpretador = new Interpretador(blocoCodigo, this.tabelaSimbolos);
            this.tabelaSimbolos = novoInterpretador.interpretar();
        }

        this.numerolinha = linhaFinal;

        return true;
    }


}
