import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public String diretorio;

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }

    public List<String> lerArquivo(String caminho) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            while (linha != null) {
                linhas.add(linha);
                linha = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return linhas;
    }

    public void gravarArquivo(String caminho, int ciclos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            bw.write(Integer.toString(ciclos));
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
}