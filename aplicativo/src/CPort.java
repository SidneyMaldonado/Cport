import java.util.List;
import java.util.Scanner;

public class CPort {
   public static void main( String[] args){

       Scanner teclado= new Scanner(System.in);
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
       if (!lexicoOK){
           return;
       }

       // executa a analise sintática
       Boolean sintaticoOK = executaAnaliseSintatica(gramatica, fonte);
       if (!sintaticoOK){
           return;
       }


   }

   public static Boolean executaAnaliseLexica(List<String> dicionario, List<String> fonte){
       // Aqui comeca o programa de Analisador Lexico
       AnalisadorLexico al = new AnalisadorLexico(dicionario, fonte);

       // Processa o analisador Lexico
       Boolean lexicoOk = al.analisar();
       if (!lexicoOk){
           for(String l: al.erros){ // imprimir erros
               System.out.println(l);
           }
           return false;
       } else {
           System.out.println("Análise Lexica: OK");
       }
       return true;
   }

   public static Boolean executaAnaliseSintatica( List<String> gramatica, List<String> fonte){

       AnalisadorSintatico as = new AnalisadorSintatico(gramatica, fonte);
       Boolean sintaticoOK = as.analisar();

       if (sintaticoOK){
           System.out.println("Análise Sintática: OK");
       } else {
           System.out.println("Análise Sintática: Erro");
       }
       return true;
   }
}
