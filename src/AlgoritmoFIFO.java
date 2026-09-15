import java.util.Comparator;
import java.util.List;

public class AlgoritmoFIFO implements Algoritmo {
    @Override
    public Metricas executar(List<Processo> processos) {
        processos.sort(Comparator.comparingInt(Processo::getTempoChegada));
        int tempoAtual = 0;
        for (Processo processo : processos) {
            tempoAtual = Math.max(tempoAtual, processo.getTempoChegada());
            processo.setTempoPrimeiraExecucao(tempoAtual);
            tempoAtual += processo.getTempoServico();
            processo.setTempoConclusao(tempoAtual - 1);
        }

        double somaResposta = 0;
        double somaEspera = 0;
        double somaTurnaroud = 0;

        for (Processo processo : processos) {
            somaResposta += processo.getTempoResposta();
            somaEspera += processo.getTempoEspera();
            somaTurnaroud += processo.getTurnaround();
        }
        int n = processos.size();
        return new Metricas(somaResposta/n, somaEspera/n,somaTurnaroud/n);
    }
}