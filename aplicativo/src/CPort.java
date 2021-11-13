import java.io.File;
import java.util.List;
import java.util.Scanner;

public class CPort {

    public static void main(String[] args) {


        Scanner teclado = new Scanner(System.in);
        String codigofonte;
        System.out.print("Digite o nome do programa:");
        codigofonte = teclado.nextLine();

        // ler o dicionario
        Arquivo arquivo = new Arquivo("dicionario.txt");
        List<String> dicionario = arquivo.lerArquivo();

        // ler o gramatica
        Arquivo arquivo2 = new Arquivo("gramatica.txt");
        List<String> gramatica = arquivo2.lerArquivo();

        // ler o codigo fonte
        Arquivo arquivo1 = new Arquivo(codigofonte); // fazer interface para o usuario
        List<String> fonte = arquivo1.lerArquivo();

        // execute a analise lexica
        Boolean lexicoOK = executaAnaliseLexica(dicionario, fonte);
        if (!lexicoOK) {
            return;
        }
        // executa a analise sintática
        Boolean sintaticoOK = executaAnaliseSintatica(gramatica, fonte);
        if (!sintaticoOK) {
            return;
        }

        // gerar uma cópia do codigo fonte
        // executar o analisador semântico

        // O que é o analisador semântico ?
        Boolean semanticoOK = executaAnaliseSemantica(fonte);
        if (!semanticoOK) {
            return;
        }


        if (args.length > 0 && args[0].equals("interpretador")) {
            executaInterpretador(fonte);
        } else {
            Boolean compiladoOK = executaCompilacao(fonte);

            if (compiladoOK){
                linkar( arquivo.base, fonte);

            }

        }

    }

    public static Boolean executaAnaliseLexica(List<String> dicionario, List<String> fonte) {
        // Aqui comeca o programa de Analisador Lexico
        AnalisadorLexico al = new AnalisadorLexico(dicionario, fonte);

        // Processa o analisador Lexico
        Boolean lexicoOk = al.analisar();
        if (!lexicoOk) {
            for (String l : al.erros) { // imprimir erros
                System.out.println(l);
            }
            return false;
        } else {
            System.out.println("Análise Lexica: OK");
        }
        return true;
    }

    public static Boolean executaAnaliseSintatica(List<String> gramatica, List<String> fonte) {

        AnalisadorSintatico as = new AnalisadorSintatico(gramatica, fonte);
        Boolean sintaticoOK = as.analisar();

        if (sintaticoOK) {
            System.out.println("Análise Sintática: OK");
        } else {
            System.out.println("Análise Sintática: Erro");
        }
        return sintaticoOK;
    }

    public static Boolean executaAnaliseSemantica(List<String> fonte) {
        AnalisadorSemantico as = new AnalisadorSemantico(fonte);
        Boolean semanticoOK = as.analisar();
        if (semanticoOK) {
            System.out.println("Análise Semantica: OK");
        } else {
            System.out.println("Análise Semantica: Erro");
        }
        return semanticoOK;
    }

    public static Boolean executaInterpretador(List<String> fonte) {

        Interpretador interpretador = new Interpretador(fonte);
        interpretador.interpretar();

        return true;
    }

    public static Boolean executaCompilacao(List<String> fonte) {
        Compilador comp = new Compilador(fonte);
        Boolean compiladoOK = comp.compilar();

        if (compiladoOK) {
            System.out.println("Compilação: OK");
        } else {
            System.out.println("Compilação: Erro");
        }
        return compiladoOK;
    }

    public static Boolean linkar(String caminho, List<String> fonte) {

        String nomeArquivo = caminho;
        String linha = fonte.get(0);
        String palavras[] = linha.split(" ");
        nomeArquivo += palavras[1].replaceAll("\"","");
        nomeArquivo += ".cpp";

        try {
            String app = nomeArquivo.replace(".cpp", ".exe");
            String linhaDeComando = "C:\\Program Files (x86)\\Dev-Cpp\\MinGW64\\bin\\c++.exe "
                    + nomeArquivo + " -o " + app;

            Runtime comando = Runtime.getRuntime();
            comando.exec(linhaDeComando);

            // falta arrumar o delete


            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static void delete(String arquivo){

        System.out.println(arquivo);
        File f  = new File(arquivo);
        f.delete();


    }

}
