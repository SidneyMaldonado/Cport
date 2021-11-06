import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Arquivo {

    public String base = "C:\\Users\\sidne\\projeto\\CPort\\";
    //public String base = "C:\\Users\\sidne\\projeto\\newlang\\";
    public String nomeArquivo;

    public Arquivo(String nome){
        this.nomeArquivo = nome;
    }

    public List<String> lerArquivo (){

        List<String> conteudo = new ArrayList();
        try {
            FileReader fr = new FileReader(this.base + this.nomeArquivo);
            Scanner entrada = new Scanner(fr);
            while (entrada.hasNext()){
                conteudo.add(entrada.nextLine());
            }
            return conteudo;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public void gravarArquivo(List<String> conteudo){

        try {

            FileWriter fw = new FileWriter(this.base + this.nomeArquivo);
            PrintWriter gravador = new PrintWriter(fw);
            for(String linha: conteudo){
                gravador.println(linha);
            }
            gravador.close();

        } catch (Exception e){
            return;
        }

    }
}
