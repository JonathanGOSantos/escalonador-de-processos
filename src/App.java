import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final String diretorio = "E:\\";
    private static final int arquivos = 3;

    public App() {
        for (int i = 1; i <= arquivos; i++) {
            List<String> linhas = lerArquivo(diretorio + "TESTE-%02d.txt".formatted(i));
            int quantum = Integer.parseInt(linhas.removeFirst());
            List<Processo> processos = linhas.stream().map(l -> new Processo(Integer.parseInt(l.split(" ")[0]), Integer.parseInt(l.split(" ")[1]))).toList();
            Metricas metricaFIFO = (new AlgoritmoFIFO()).executar(processos);
            Metricas metricaSJF = (new AlgoritmoSJF()).executar(processos);
            Metricas metricaSRT = (new AlgoritmoSRT()).executar(processos);
            Metricas metricaRR = (new AlgoritmoRR(quantum)).executar(processos);
            gravarArquivo(diretorio + "TESTE-%02d-RESULTADO.txt".formatted(i), List.of(metricaFIFO.toString(), metricaSJF.toString(), metricaSRT.toString(), metricaRR.toString()));
        }
    }

    public List<String> lerArquivo(String caminho) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    linhas.add(linha.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + caminho + ": " + e.getMessage());
        }
        return linhas;
    }

    public void gravarArquivo(String caminho, List<String> linhas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            for (String linha : linhas) {
                bw.write(linha + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo " + caminho + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new App();
    }
}