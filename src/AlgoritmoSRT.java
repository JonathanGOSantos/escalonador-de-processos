import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AlgoritmoSRT implements Algoritmo {
    @Override
    public Metricas executar(List<Processo> processos) {
        Queue<Processo> fila = new PriorityQueue<>(Comparator.comparingInt(Processo::getTempoRestante).thenComparingInt(Processo::getTempoChegada));

        AtomicInteger indiceProximoProcesso = new AtomicInteger(0);
        int tempo = 0;
        while (indiceProximoProcesso.get() < processos.size() || !fila.isEmpty()) {
            fila.addAll(Processo.getProcessosEm(processos, tempo, indiceProximoProcesso));
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
