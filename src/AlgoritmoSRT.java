import java.util.*;

public class AlgoritmoSRT implements Algoritmo {
    @Override
    public Metricas executar(List<Processo> processos) {
        Queue<Processo> fila = new PriorityQueue<>(Comparator.comparingInt(Processo::getTempoRestante).thenComparingInt(Processo::getTempoChegada));

        int ultimoAChegar = Processo.getUltimoAChegar(processos);
        int tempo = 0;
        while (tempo <= ultimoAChegar || !fila.isEmpty()) {
            fila.addAll(Processo.getProcessosEm(processos, tempo));
            Processo primeiro = fila.peek();
            if (primeiro != null) {
                fila.remove(primeiro);
                primeiro.setTempoPrimeiraExecucao(tempo);
                primeiro.executar();
                if (primeiro.concluido()) {
                    primeiro.setTempoConclusao(tempo);
                } else {
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
