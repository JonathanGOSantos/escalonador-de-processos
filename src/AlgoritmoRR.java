import java.util.*;

public class AlgoritmoRR implements Algoritmo {
    private int quantum;

    public AlgoritmoRR(int quantum) {
        if (quantum <= 0) {
            throw new IllegalArgumentException("Quantum deve ser maior que 0");
        }
        this.quantum = quantum;
    }

    @Override
    public Metricas executar(List<Processo> processos) {
        Queue<Processo> fila = new LinkedList<>();
        int ultimoAChegar = Processo.getUltimoAChegar(processos);
        int tempo = 0;
        int execucoes = 0;
        while (tempo <= ultimoAChegar || !fila.isEmpty()) {
            int finalTempo = tempo;
            fila.addAll(Processo.getProcessosEm(processos, tempo));
            Processo primeiro = fila.peek();
            if (primeiro != null) {
                primeiro.setTempoPrimeiraExecucao(tempo);
                primeiro.executar();
                execucoes++;
                if (primeiro.concluido()) {
                    fila.remove(primeiro);
                    primeiro.setTempoConclusao(tempo);
                    execucoes = 0;
                } else if (execucoes == quantum) {
                    fila.remove(primeiro);
                    execucoes = 0;
                    fila.add(primeiro);
                }
            }
            tempo++;
        }

        double mediaTempoResposta = Processo.getMediaTempoResposta(processos);
        double mediaTempoEspera = Processo.getMediaTempoEspera(processos);
        double mediaTurnaround = Processo.getMediaTurnaround(processos);
        return new Metricas(mediaTempoResposta, mediaTempoEspera, mediaTurnaround);
    }
}
