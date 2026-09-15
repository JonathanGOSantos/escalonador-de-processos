import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final String diretorio = "C:\\Users\\0118303\\OneDrive - Instituto Federal de Minas Gerais\\Sistemas Operacionais\\escalonador-de-processos\\src\\";
    private static final int arquivos = 3;

    public App() {
        for (int i = 1; i <= arquivos; i++) {
            try {
                List<String> linhas = lerArquivo(diretorio + "TESTE-%02d.txt".formatted(i));
                int quantum = Integer.parseInt(linhas.removeFirst());
                List<Processo> processos = linhas.stream().map(l -> new Processo(Integer.parseInt(l.split(" ")[0]), Integer.parseInt(l.split(" ")[1]))).toList();

                List<Processo> processosFIFO = duplicarProcessos(processos);
                List<Processo> processosSJF = duplicarProcessos(processos);
                List<Processo> processosSRT = duplicarProcessos(processos);
                List<Processo> processosRR = duplicarProcessos(processos);

                Metricas metricaFIFO = (new AlgoritmoFIFO()).executar(processosFIFO);
                Metricas metricaSJF = (new AlgoritmoSJF()).executar(processosSJF);
                Metricas metricaSRT = (new AlgoritmoSRT()).executar(processosSRT);
                Metricas metricaRR = (new AlgoritmoRR(quantum)).executar(processosRR);
                gravarArquivo(diretorio + "TESTE-%02d-RESULTADO.txt".formatted(i), List.of(metricaFIFO.toString(), metricaSJF.toString(), metricaSRT.toString(), metricaRR.toString()));
            } catch (Exception e) {
                System.err.printf("Arquivo %d: %s\n", i, e.getMessage());
            }
        }
    }

    private static List<Processo> duplicarProcessos(List<Processo> processos) {
        return processos.stream().map(p -> new Processo(p.getTempoChegada(), p.getTempoServico())).toList();
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